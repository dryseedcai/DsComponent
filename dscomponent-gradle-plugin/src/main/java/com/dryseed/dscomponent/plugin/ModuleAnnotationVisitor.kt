package com.dryseed.dscomponent.plugin

import org.objectweb.asm.AnnotationVisitor
import java.util.*

class ModuleAnnotationVisitor internal constructor(api: Int, av: AnnotationVisitor, private val nameString: String?,
                                                   private val valueMap: HashMap<String, String>) : AnnotationVisitor(api, av) {

    override fun visit(name: String, value: Any) {
        super.visit(name, value)
        if ("register" == name) {
            nameString?.let {
                valueMap[value as String] = it
            }
        } else if ("desc" == name) {
            //todo show desc
        }
    }
}
