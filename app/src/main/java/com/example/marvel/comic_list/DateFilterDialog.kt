package com.example.marvel.comic_list


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.marvel.R
import java.util.*

class DateFilterDialog(val filter: Filter) : DialogFragment() {

    var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_date_picker, null)
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)


        val calendar = Calendar.getInstance()
        calendar.time = filter.date
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        )
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(context)
            .setPositiveButton("Ок", perform())
            .setNegativeButton("Отмена", null).create()
        return dialog
    }

    private fun perform() = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            listener?.let { listener ->
                val datePicker = view?.findViewById<DatePicker>(R.id.datePicker)
                val calendar = Calendar.getInstance()

                datePicker?.let {
                    calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                    val date = calendar.time
                    listener.onChange(Filter(date, filter.isStartDate))
                }
            }//todo
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window!!.attributes = params as WindowManager.LayoutParams
    }

    companion object {
        interface Listener {
            fun onChange(filter: Filter)
        }
    }
}


data class Filter(
    val date: Date,
    val isStartDate: Boolean
)