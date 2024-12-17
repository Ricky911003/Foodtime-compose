import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewModelScope
import com.example.foodtime_compose0518.SettingTable
import com.example.foodtime_compose0518.StockTable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TextFieldWithDropdown(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    setValue: (TextFieldValue) -> Unit,
    onDismissRequest: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<String>,
    label: String = ""
) {
    Box(modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        onDismissRequest()
                    }
                },
            value = value,
            onValueChange = setValue,
            label = { Text(label) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray
            )
        )
        if (dropDownExpanded) {
            DropdownMenu(
                expanded = dropDownExpanded,
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = onDismissRequest
            ) {
                val filteredList = list.filter {
                    it.contains(value.text, ignoreCase = true)
                }
                if (filteredList.isEmpty()) {
//                    DropdownMenuItem(onClick = {}) {
//                        Text("No options available")
//                    }
                } else {
                    filteredList.forEach { text ->
                        DropdownMenuItem(onClick = {
                            setValue(
                                TextFieldValue(
                                    text,
                                    TextRange(text.length)
                                )
                            )
                        },) {
                            Text(text = text)
                        }
                    }
                }
            }
        }
    }
}




val all = listOf("aaa", "baa", "aab", "abb", "bab")

val dropDownOptions = mutableStateOf(listOf<String>())
val textFieldValue = mutableStateOf(TextFieldValue())
val dropDownExpanded = mutableStateOf(false)
fun onDropdownDismissRequest() {
    dropDownExpanded.value = false
}

fun onValueChanged(value: TextFieldValue) {
    dropDownExpanded.value = true
    textFieldValue.value = value
    dropDownOptions.value = all.filter { it.startsWith(value.text) && it != value.text }.take(3)
}




@Preview
@Composable
fun TextFieldWithDropdownUsage() {
    TextFieldWithDropdown(
        modifier = Modifier.fillMaxWidth(),
        value = textFieldValue.value,
        setValue = ::onValueChanged,
        onDismissRequest = ::onDropdownDismissRequest,
        dropDownExpanded = dropDownExpanded.value,
        list = dropDownOptions.value,
        label = "Label"
    )
}



fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}


//// 預設食材資料
//private val defaultSettings = listOf(
//    SettingTable(settingDay = 7, settingName = "豆芽菜", settingNotify = true),
//    SettingTable(settingDay = 14, settingName = "牛肉", settingNotify = true),
//    SettingTable(settingDay = 5, settingName = "花椰菜", settingNotify = true),
//    SettingTable(settingDay = 9, settingName = "高麗菜", settingNotify = true),
//    SettingTable(settingDay = 28, settingName = "雞肉", settingNotify = false),
//    SettingTable(settingDay = 2, settingName = "蛤蜊", settingNotify = false),
//    SettingTable(settingDay = 5, settingName = "鱈魚", settingNotify = false),
//    SettingTable(settingDay = 28, settingName = "蛋", settingNotify = false),
//    SettingTable(settingDay = 7, settingName = "茄子", settingNotify = false),
//    SettingTable(settingDay = 7, settingName = "魚", settingNotify = false),
//    SettingTable(settingDay = 30, settingName = "洋蔥", settingNotify = false),
//    SettingTable(settingDay = 7, settingName = "蒜頭", settingNotify = false),
//    SettingTable(settingDay = 28, settingName = "豬肉", settingNotify = false),
//    SettingTable(settingDay = 28, settingName = "蘿蔔", settingNotify = false),
//    SettingTable(settingDay = 12, settingName = "紅蘿蔔", settingNotify = false),
//    SettingTable(settingDay = 12, settingName = "鮭魚", settingNotify = false),
//    SettingTable(settingDay = 12, settingName = "蝦", settingNotify = false),
//    SettingTable(settingDay = 5, settingName = "豆腐", settingNotify = false),
//    SettingTable(settingDay = 9, settingName = "番茄", settingNotify = false),
//)
//
//
//init {
//    loadDefaultSettings()
//}
//
///**
// * 初始化資料庫，如果資料庫中沒有資料則插入預設值
// */
//private fun loadDefaultSettings() {
//    viewModelScope.launch {
//        val existingSettings = dao.getAllUsers().first()
//        val existingNames = existingSettings.map { it.settingName }
//        val newSettings = defaultSettings.filter { it.settingName !in existingNames }
//
//        // 插入新的預設資料，ID 會自動生成
//        newSettings.forEach { dao.insert(it) }
//    }
//}

//fun addStockItem() {
//    viewModelScope.launch {
//        val datalist =
//            StockTable(
//                stockitemName = newStockName,
////                    number = newNumber,
//                loginDate = newLoginDate,
//                expiryDate = newExpiryDate,
//                uuid = newuuid
//
//            )
//        dao.insert(listOf(datalist))
//    }
//}