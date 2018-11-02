package com.dryseed.dscomponent.plugin

import com.dryseed.dscomponent.annotations.ModuleFragment
import com.dryseed.dscomponent.annotations.ModuleService
import com.dryseed.dscomponent.annotations.ModuleView
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import java.util.*

class DsComponentVisitor(api: Int, cv: ClassVisitor) : ClassVisitor(api, cv) {

    var serviceMap = HashMap<String, String>()
    var viewMap = HashMap<String, String>()
    var fragmentMap = HashMap<String, String>()
    private var className: String? = null

    override fun visit(
        version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces:
        Array<String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        val annotationVisitor = super.visitAnnotation(desc, visible)
        if (Type.getDescriptor(ModuleService::class.java) == desc) {
            return ModuleAnnotationVisitor(Opcodes.ASM5, annotationVisitor, className, serviceMap)
        }
        if (Type.getDescriptor(ModuleFragment::class.java) == desc) {
            return ModuleAnnotationVisitor(Opcodes.ASM5, annotationVisitor, className, fragmentMap)
        }
        return if (Type.getDescriptor(ModuleView::class.java) == desc) {
            ModuleAnnotationVisitor(Opcodes.ASM5, annotationVisitor, className, viewMap)
        } else annotationVisitor
    }

}
