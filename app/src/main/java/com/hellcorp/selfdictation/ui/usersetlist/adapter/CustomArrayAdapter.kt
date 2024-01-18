package com.hellcorp.selfdictation.ui.usersetlist.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomArrayAdapter(context: Context, resource: Int, objects: Array<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.textSize = 22f
        view.setPadding(30, 10, 20, 10)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.textSize = 22f
        view.setPadding(30, 10, 20, 10)
        view.shadowRadius
        view.cameraDistance
        view.verticalFadingEdgeLength
        return view
    }
}
