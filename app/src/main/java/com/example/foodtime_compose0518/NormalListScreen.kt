package com.example.foodtime_compose0518

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodtime_compose0518.ui.theme.bodyFontFamily
import com.example.foodtime_compose0518.ui.theme.displayFontFamily
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp


// 定义 Note2 数据类
data class Note2(val id: Int, val productname: String, var number: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem2(
    note: Note2,
    cover1: Int,
    onClick: (Note2) -> Unit,
    onRemove: (Note2) -> Unit
) {
    val context = LocalContext.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onRemove(note)
                    Toast.makeText(context, "項目已刪除", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        },
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(
        state = dismissState,
        content = {
            NoteContent(note, cover1, onClick)
        },
        backgroundContent = { DismissBackground(dismissState) },
    )
}

@Composable
fun NoteContent(note: Note2, cover1: Int, onClick: (Note2) -> Unit) {
    val quantity = remember { mutableStateOf(note.number.toString()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(cover1),
            contentDescription = "Note cover 1",
            modifier = Modifier
                .size(50.dp)
                .padding(start = 16.dp)
                .clickable { onClick(note) }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = note.productname,
            fontFamily = displayFontFamily,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (note.number > 0) {
                    note.number--
                    quantity.value = note.number.toString()
                }
            }) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "减少数量")
            }

            OutlinedTextField(
                value = quantity.value,
                onValueChange = { newValue ->
                    // 确保输入的是有效的数字
                    val number = newValue.toIntOrNull()
                    if (number != null && number >= 0) {
                        note.number = number
                        quantity.value = newValue
                    } else if (newValue.isEmpty()) {
                        quantity.value = ""
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier.width(50.dp),
                textStyle = TextStyle(
                    fontFamily = bodyFontFamily,
                    fontSize = 16.sp
                )
            )

            IconButton(onClick = {
                note.number++
                quantity.value = note.number.toString()
            }) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "增加数量")
            }
        }
    }
}









@Composable
fun NoteList2(navController: NavController) {
    // 使用 Note2 数据类
    val notes = remember { mutableStateListOf(
        Note2(id = 1, productname = "蘋果", number = 5),
        Note2(id = 2, productname = "花椰菜", number = 2),
        Note2(id = 3, productname = "牛肉", number = 3),
        Note2(id = 4, productname = "蘋果", number = 7)
    ) }

    LazyColumn {
        items(notes, key = { it.id }) { note ->
            NoteItem2(
                note = note,
                cover1 = R.drawable.apple,
                onClick = { navController.navigate("FoodDetail") },
                onRemove = { notes.remove(it) }
            )
            Divider()
        }
    }
}



@Composable
fun Normallist(navController: NavController) {
    Column {
        NoteList2(navController = navController)
        Spacer(modifier = Modifier.weight(1f))
        Padding16dp {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate("AddFragment")
                },
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        "Extended floating action button."
                    )
                },
                text = { Text(text = "新增食材") },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NormalListPreview() {
    val navController = rememberNavController()
    Normallist(navController = navController)
}
