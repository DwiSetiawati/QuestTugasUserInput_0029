package com.example.act4

import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.act4.ui.theme.Act4Theme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBiodata(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // ===== STATE: tempat menyimpan isi form =====
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var skillAndroid by remember { mutableStateOf(false) }
    var skillUiux by remember { mutableStateOf(false) }
    var skillWeb by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

    val experienceOptions = stringArrayResource(R.array.experience_options)

    // Tombol aktif kalau semua terisi + minimal 1 skill dipilih
    val isFormValid = firstName.isNotBlank() && lastName.isNotBlank() &&
            email.isNotBlank() && birthDate.isNotBlank() && experience.isNotBlank() &&
            (skillAndroid || skillUiux || skillWeb)

    val fieldShape = RoundedCornerShape(dimensionResource(R.dimen.field_corner))
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(R.color.purple_title),
        unfocusedBorderColor = colorResource(R.color.field_border),
        focusedTextColor = colorResource(R.color.text_primary),
        unfocusedTextColor = colorResource(R.color.text_primary)
    )

    Box(modifier = modifier.fillMaxSize()) {

        // 1. Gambar latar
        Image(
            painter = painterResource(R.drawable.bunga),
            contentDescription = stringResource(R.string.cd_background),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Indikator titik-titik
        PageIndicator(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(dimensionResource(R.dimen.indicator_padding))
        )

        // 3. Box putih yang MENGAPUNG di atas Scaffold
        val cardShape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.card_corner),
            topEnd = dimensionResource(R.dimen.card_corner)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .shadow(dimensionResource(R.dimen.card_elevation), cardShape)
                .background(colorResource(R.color.white), cardShape)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.card_padding)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.field_spacing))
            ) {
                Text(
                    text = stringResource(R.string.form_title),
                    fontSize = dimensionResource(R.dimen.text_title_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.purple_title)
                )
                Text(
                    text = stringResource(R.string.form_subtitle),
                    fontSize = dimensionResource(R.dimen.text_subtitle_size).value.sp,
                    color = colorResource(R.color.text_subtitle),
                    textAlign = TextAlign.Center
                )

                // Nama Depan | Nama Belakang
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.field_spacing))) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text(stringResource(R.string.first_name)) },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text(stringResource(R.string.last_name)) },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.email)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = fieldShape,
                    colors = fieldColors,
                    modifier = Modifier.fillMaxWidth()
                )

                // Tgl Lahir | Experience
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.field_spacing))) {
                    OutlinedTextField(
                        value = birthDate,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.birth_date)) },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_calendar),
                                    contentDescription = stringResource(R.string.cd_pick_date)
                                )
                            }
                        },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        modifier = Modifier.weight(1f)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = experience,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(stringResource(R.string.experience)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            singleLine = true,
                            shape = fieldShape,
                            colors = fieldColors,
                            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            experienceOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        experience = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Skill
                Text(
                    text = stringResource(R.string.choose_skill),
                    fontSize = dimensionResource(R.dimen.text_label_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_primary),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SkillCheckbox(
                        stringResource(R.string.skill_android),
                        skillAndroid
                    ) { skillAndroid = it }
                    SkillCheckbox(stringResource(R.string.skill_uiux), skillUiux) { skillUiux = it }
                    SkillCheckbox(stringResource(R.string.skill_web), skillWeb) { skillWeb = it }
                }

                // Tombol Proceed
                Button(
                    onClick = { showResult = true },
                    enabled = isFormValid,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_title),
                        contentColor = colorResource(R.color.white),
                        disabledContainerColor = colorResource(R.color.button_disabled),
                        disabledContentColor = colorResource(R.color.white)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.button_height))
                ) {
                    Text(
                        text = stringResource(R.string.proceed),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // ===== Dialog kalender =====
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        val dateFormat = stringResource(R.string.date_format)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
                        formatter.timeZone = TimeZone.getTimeZone("UTC")
                        birthDate = formatter.format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text(stringResource(R.string.dialog_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.dialog_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}