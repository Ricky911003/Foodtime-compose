package com.example.foodtime_compose0518

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodtime_compose0518.ui.theme.Foodtime_compose0518Theme
import com.example.foodtime_compose0518.ui.theme.bodyFontFamily
import com.example.foodtime_compose0518.ui.theme.displayFontFamily
import com.example.foodtime_compose0518.ui.theme.myprimary1
import com.example.foodtime_compose0518.ui.theme.onPrimaryLight

//class NormalAddScreen : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            Foodtime_compose0518Theme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting5("Android")
//                }
//            }
//        }
//    }
//}




@Composable
fun AddScreen(navController: NavHostController) {
    Foodtime_compose0518Theme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFf0ebf5))
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // 食材名稱输入框
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "食材名稱",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 10.dp),
                    fontFamily = displayFontFamily
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "輸入食材名稱") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "數量",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 10.dp),
                    fontFamily = displayFontFamily
                )
                Spacer(modifier = Modifier.width(50.dp))
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        Icons.Outlined.KeyboardArrowUp,
                        contentDescription = "Localized description"
                    )
                }

                OutlinedTextField(
                    value = "1",
                    onValueChange = {},
                    label = { },
                    modifier = Modifier
                        .width(120.dp)
                        .padding(horizontal = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Localized description"
                    )
                }
            }

            Spacer(modifier = Modifier.height(270.dp))

            Row {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        myprimary1 // 使用您定义的颜色
                    ),
                    onClick = {
                        // 回到 Normallist 屏幕
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .padding(horizontal = 30.dp)
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(35.dp) // 设置按钮的弧度
                ) {
                    Text(
                        "增加食材",
                        fontSize = 16.sp,
                        fontFamily = bodyFontFamily
                    )
                }

                Button(
                    onClick = {
                        // 回到 Normallist 屏幕
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        onPrimaryLight // 使用您定义的颜色
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .padding(horizontal = 30.dp)
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(35.dp) // 设置按钮的弧度
                ) {
                    Text(
                        text = "取消",
                        fontSize = 16.sp,
                        fontFamily = bodyFontFamily,
                        style = TextStyle(color = myprimary1)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NormalAddScreenPreview() {
    val navController = rememberNavController()
    AddScreen(navController)
}
