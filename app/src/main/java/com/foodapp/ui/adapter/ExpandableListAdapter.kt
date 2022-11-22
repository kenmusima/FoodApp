package com.foodapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.foodapp.data.db.entity.Order
import com.foodapp.databinding.ListGroupBinding
import com.foodapp.databinding.ListOrdersBinding

class ExpandableListAdapter internal constructor(
    context: Context,
    private val expandableListTitle: List<String>,
    private val expandableListDetail: HashMap<String, List<Order>>
) : BaseExpandableListAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var groupBinding: ListGroupBinding
    private lateinit var itemBinding: ListOrdersBinding

    override fun getGroupCount(): Int = expandableListTitle.size

    override fun getChildrenCount(groupPosition: Int): Int = expandableListDetail[expandableListTitle[groupPosition]]!!.size

    override fun getGroup(groupPosition: Int): Any = expandableListTitle[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any =
        expandableListDetail[expandableListTitle[groupPosition]]!![childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView

        val holder: GroupViewHolder
        if (convertView == null) {
            groupBinding = ListGroupBinding.inflate(inflater)
            convertView = groupBinding.root
            holder = GroupViewHolder()
            holder.label = groupBinding.listOrderTitle
            convertView.tag = holder
        } else {
            holder = convertView.tag as GroupViewHolder
        }
        val listTitle = getGroup(groupPosition) as String
        holder.label!!.text = listTitle

        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val holder: ItemViewHolder

        if (convertView == null) {
            itemBinding = ListOrdersBinding.inflate(inflater)
            convertView = itemBinding.root
            holder = ItemViewHolder()
            holder.label = itemBinding.expandedListItem
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemViewHolder
        }

        val expandedListText = getChild(groupPosition, childPosition)
        holder.label!!.text = (expandedListText as Order).toString()
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    inner class ItemViewHolder {
        internal var label: TextView? = null
    }

    inner class GroupViewHolder {
        internal var label: TextView? = null
    }
}