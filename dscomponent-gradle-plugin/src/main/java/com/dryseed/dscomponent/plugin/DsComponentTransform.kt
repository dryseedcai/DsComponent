package com.dryseed.dscomponent.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry


class DsComponentTransform : Transform() {
    private var serviceMap = HashMap<String, String>()
    private var viewMap = HashMap<String, String>()
    private var fragmentMap = HashMap<String, String>()

    override fun getName(): String {
        return "DsComponentTransform"
    }

    override fun getInputTypes(): Set<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope>? {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }

    private fun handleClassFile(file: File, name: String) {
        if (file.isDirectory) {
            for (tmp in file.listFiles()) {
                handleClassFile(tmp, name)
            }
        } else {
            val fileName = file.name
            if (fileName.endsWith(".class")) {
                handleClassInputStream(FileInputStream(file))
            }
        }
    }

    private fun handleClassInputStream(inputStream: InputStream?) {
        val classReader = ClassReader(inputStream)
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val msmVisitor = DsComponentVisitor(Opcodes.ASM5, classWriter)
        classReader.accept(msmVisitor, ClassReader.EXPAND_FRAMES)
        serviceMap.putAll(msmVisitor.serviceMap)
        viewMap.putAll(msmVisitor.viewMap)
        fragmentMap.putAll(msmVisitor.fragmentMap)
        inputStream?.close()
    }


    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)

        println("==============> transform")
        transformInvocation.inputs.forEach { input ->
            input.directoryInputs.forEach { directoryInput ->
                if (directoryInput.file.isDirectory) {
                    handleClassFile(directoryInput.file, name)
                }
                val dest = transformInvocation.outputProvider.getContentLocation(
                    directoryInput.name, directoryInput
                        .contentTypes, directoryInput.scopes, Format.DIRECTORY
                )
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.forEach { jarInput ->
                val filePath = jarInput.file.absolutePath
                if (filePath.endsWith(".jar")) {
                    val jarFile = JarFile(jarInput.file)
                    val enumeration = jarFile.entries()
                    while (enumeration.hasMoreElements()) {
                        val jarEntry = enumeration.nextElement() as JarEntry
                        val entryName = jarEntry.name
                        if (entryName.endsWith(".class")) {
                            val inputStream = jarFile.getInputStream(jarEntry)
                            handleClassInputStream(inputStream)
                        }
                    }
                    jarFile.close()
                }
                var jarName = jarInput.name
                val md5Name = DigestUtils.md5Hex(jarInput.file.absolutePath)
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length - 4)
                }
                val dest = transformInvocation.outputProvider.getContentLocation(
                    jarName + md5Name, jarInput
                        .contentTypes,
                    jarInput.scopes, Format.JAR
                )
                FileUtils.copyFile(jarInput.file, dest)
            }

            val moduleInfoWriter = ModuleInfoWriter()
            val metaFile = transformInvocation.outputProvider.getContentLocation(
                "Msm", outputTypes, scopes
                , Format.JAR
            )
            if (!metaFile.parentFile.exists()) {
                metaFile.parentFile.mkdirs()
            }
            if (metaFile.exists()) {
                metaFile.delete()
            }

            println("==============> " + metaFile.absoluteFile.canonicalPath)
            println(
                String.format(
                    "==============> serviceMap : %d | viewMap : %d | fragmentMap : %d",
                    serviceMap.size, viewMap.size, fragmentMap.size
                )
            )
            for ((k, v) in serviceMap) {
                println("==============>  $k -> $v")
            }

            val fos = FileOutputStream(metaFile)
            val jarOutputStream = JarOutputStream(fos)
            val zipEntry = ZipEntry("com/dryseed/dscomponent/api/DefaultModuleIndex.class")
            jarOutputStream.putNextEntry(zipEntry)
            jarOutputStream.write(moduleInfoWriter.dump(serviceMap, viewMap, fragmentMap))
            jarOutputStream.closeEntry()
            jarOutputStream.close()
            fos.close()

        }

    }
}