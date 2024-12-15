package com.example.foodtime_compose0518

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingViewModel(val dao: SettingDao) : ViewModel() {

    // 使用 Compose 的 mutableStateOf 持有 UI 狀態
    var newSettingName by mutableStateOf("")
    var newSettingDay by mutableStateOf(0)
    var newSettingBoolean by mutableStateOf(false)

    // 使用 Flow 持有設定資料，提供給 UI 更新
    val settingList: StateFlow<List<SettingTable>> = dao.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 預設食材資料
    private val defaultSettings = listOf(
        SettingTable(settingDay = 3, settingName = "紅燈", settingNotify = false),
        SettingTable(settingDay = 5, settingName = "黃燈", settingNotify = false),
        SettingTable(settingDay = 7, settingName = "綠燈", settingNotify = false),
        SettingTable(settingDay = 7, settingName = "豆芽菜", settingNotify = true),
        SettingTable(settingDay = 14, settingName = "牛肉", settingNotify = true),
        SettingTable(settingDay = 5, settingName = "花椰菜", settingNotify = true),
        SettingTable(settingDay = 9, settingName = "高麗菜", settingNotify = true),
        SettingTable(settingDay = 28, settingName = "雞肉", settingNotify = false),
        SettingTable(settingDay = 2, settingName = "蛤蜊", settingNotify = false),
        SettingTable(settingDay = 5, settingName = "鱈魚", settingNotify = false),
        SettingTable(settingDay = 28, settingName = "蛋", settingNotify = false),
        SettingTable(settingDay = 7, settingName = "茄子", settingNotify = false),
        SettingTable(settingDay = 7, settingName = "魚", settingNotify = false),
        SettingTable(settingDay = 30, settingName = "洋蔥", settingNotify = false),
        SettingTable(settingDay = 7, settingName = "蒜頭", settingNotify = false),
        SettingTable(settingDay = 28, settingName = "豬肉", settingNotify = false),
        SettingTable(settingDay = 28, settingName = "蘿蔔", settingNotify = false),
        SettingTable(settingDay = 12, settingName = "紅蘿蔔", settingNotify = false),
        SettingTable(settingDay = 12, settingName = "鮭魚", settingNotify = false),
        SettingTable(settingDay = 12, settingName = "蝦", settingNotify = false),
        SettingTable(settingDay = 5, settingName = "豆腐", settingNotify = false),
        SettingTable(settingDay = 9, settingName = "番茄", settingNotify = false)
    )

    init {
        loadDefaultSettings()
    }

    /**
     * 初始化資料庫，如果資料庫中沒有資料則插入預設值
     */
    private fun loadDefaultSettings() {
        viewModelScope.launch {
            val existingSettings = dao.getAllUsers().first()
            val existingNames = existingSettings.map { it.settingName }
            val newSettings = defaultSettings.filter { it.settingName !in existingNames }

            // 插入新的預設資料
            newSettings.forEach { dao.insert(it) }
        }
    }

    /**
     * 設定新設定名稱
     */
    fun setSettingName(name: String) {
        newSettingName = name
    }

    /**
     * 設定新設定的天數
     */
    fun setSettingDay(day: Int) {
        newSettingDay = day
    }

    /**
     * 設定新設定的通知狀態
     */
    fun setSettingBoolean(notify: Boolean) {
        newSettingBoolean = notify
    }

    /**
     * 新增設定
     */
    fun addSetting() {
        viewModelScope.launch {
            dao.insert(
                SettingTable(
                    settingName = newSettingName,
                    settingDay = newSettingDay,
                    settingNotify = newSettingBoolean
                )
            )
        }
    }

    /**
     * 更新設定
     */
    fun updateSettingItem(setting: SettingTable) {
        viewModelScope.launch {
            dao.update(setting)
        }
    }

    fun updateSetting(name: String, notify: Boolean, day: Int) {
        viewModelScope.launch {
            val setting = dao.getSettingByName(name)
            if (setting != null) {
                val updatedSetting = setting.copy(settingNotify = notify, settingDay = day)
                dao.update(updatedSetting)
            }
        }
    }

    /**
     * 更新特定設定的天數
     */
    fun updateSettingDay(name: String, day: Int) {
        viewModelScope.launch {
            val setting = dao.getSettingByName(name)
            setting?.let {
                val updatedSetting = it.copy(settingDay = day)
                dao.update(updatedSetting)
            }
        }
    }

    /**
     * 更新食材過期天數
     */
    fun updateFoodExpiration(settingName: String, newDays: Int) {
        viewModelScope.launch {
            val setting = dao.getSettingByName(settingName)
            setting?.let {
                val updatedSetting = it.copy(settingDay = newDays)
                dao.update(updatedSetting)
            }
        }
    }

    /**
     * 刪除設定
     */
    fun deleteSettingItem(setting: SettingTable) {
        viewModelScope.launch {
            dao.delete(setting)
        }
    }

    /**
     * 禁用所有通知
     */
    fun disableAllNotifications() {
        viewModelScope.launch {
            val settings = dao.getAllUsers().first()
            settings.forEach { setting ->
                val updatedSetting = setting.copy(settingNotify = false)
                dao.update(updatedSetting)
            }
        }
    }
}
