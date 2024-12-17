package com.example.foodtime_compose0518

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class SettingViewModel(val dao: SettingDao) : ViewModel() {
    var newSettingName = ""
    var newSettingDay = 0
    var newSettingBoolean = false


    val settingList: Flow<List<SettingTable>> = dao.getAllUsers().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    fun setSettingName(name: String) {
        newSettingName = name
    }

    fun setSettingDay(day: Int) {
        newSettingDay = day
    }

    fun setSettingBoolean(notify: Boolean) {
        newSettingBoolean = notify
    }

    fun addSetting() {
        viewModelScope.launch {
            dao.insert(
                SettingTable(
                    settingName = newSettingName,
                    settingNotify =  newSettingBoolean,
                    settingDay = newSettingDay
                )
            )
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

    fun updateSettingDay(name: String, day: Int) {
        viewModelScope.launch {
            val setting = dao.getSettingByName(name)
            if (setting != null) {
                val updatedSetting = setting.copy(settingDay = day)
                dao.update(updatedSetting)
            }
        }
    }

    fun deleteSetting(setting: SettingTable) {
        viewModelScope.launch {
            dao.delete(setting)
        }
    }

    fun insertSetting(setting: SettingTable) {
        viewModelScope.launch {
            dao.insert(setting)
        }
    }

    fun updateFoodExpiration(settingName: String, newDays: Int) {
        viewModelScope.launch {
            val setting = dao.getSettingByName(settingName)
            if (setting != null) {
                val updatedSetting = setting.copy(settingDay = newDays)
                dao.update(updatedSetting)
            }
        }
    }

    init {
        initializeSettings()
    }

    private fun initializeSettings() {
        viewModelScope.launch {
            val existingSettings = dao.getAllUsers().firstOrNull() // 取得目前的設定資料
            if (existingSettings.isNullOrEmpty()) {
                // 初始化三筆資料
                dao.insert(SettingTable(settingName = "RedLightEnabled", settingNotify = true, settingDay = 20))
                dao.insert(SettingTable(settingName = "YellowLightEnabled", settingNotify = true, settingDay = 50))
                dao.insert(SettingTable(settingName = "NotificationEnabled", settingNotify = true, settingDay = 0))
            }
        }
    }

    fun disableAllNotifications() {
        viewModelScope.launch {
            // 更新三個設定項目
            dao.update(SettingTable(settingName = "NotificationEnabled", settingNotify = false, settingDay = 0))
            dao.update(SettingTable(settingName = "RedLightEnabled", settingNotify = false, settingDay = 3)) // 保留 day 的值
            dao.update(SettingTable(settingName = "YellowLightEnabled", settingNotify = false, settingDay = 5)) // 保留 day 的值
        }
    }

    // 預設食材資料
    private val defaultSettings = listOf(
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
        SettingTable(settingDay = 9, settingName = "番茄", settingNotify = false),
        SettingTable(settingDay = 9, settingName = "軟體動物", settingNotify = false),

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

            // 插入新的預設資料，ID 會自動生成
            newSettings.forEach { dao.insert(it) }
        }
    }

}