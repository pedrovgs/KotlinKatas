package com.github.pedrovgs.formvalidation

import arrow.data.Validated
import arrow.data.NonEmptyList
import arrow.data.ValidatedNel
import arrow.data.invalidNel
import arrow.data.valid
import arrow.data.extensions.nonemptylist.semigroup.semigroup
import arrow.data.extensions.validated.applicative.applicative
import arrow.data.fix
import java.time.LocalDateTime

typealias FormValidationResult<T> = ValidatedNel<FormError, T>

object FormValidator {

    fun validateForm(referenceDate: LocalDateTime, form: Form): FormValidationResult<Form> =
            Validated.applicative(NonEmptyList.semigroup<FormError>()).map(
                    validateFirstName(form.firstName),
                    validateLastName(form.lastName),
                    validateBirthday(referenceDate, form.birthday),
                    validateDocumentId(form.documentId),
                    validatePhoneNumber(form.phoneNumber),
                    validateEmail(form.email)) {
                Form(it.a, it.b, it.c, it.d, it.e, it.f)
            }.fix()

    private fun validateFirstName(firstName: String): FormValidationResult<String> =
            if (firstName.trim().isNotEmpty()) {
                Validated.Valid(firstName)
            } else {
                FormError.EmptyFirstName(firstName).invalidNel()
            }

    private fun validateLastName(lastName: String): FormValidationResult<String> =
            if (lastName.trim().isNotEmpty()) {
                lastName.valid()
            } else {
                FormError.EmptyLastName(lastName).invalidNel()
            }

    private fun validateBirthday(referenceDate: LocalDateTime, birthday: LocalDateTime): FormValidationResult<LocalDateTime> =
            if (birthday <= referenceDate.minusYears(18)) {
                birthday.valid()
            } else {
                FormError.UserTooYoung(birthday).invalidNel()
            }

    private fun validateDocumentId(documentId: String): FormValidationResult<String> =
            if (documentRegex.matches(documentId)) {
                documentId.valid()
            } else {
                FormError.InvalidDocumentId(documentId).invalidNel()
            }

    private fun validatePhoneNumber(phoneNumber: String): FormValidationResult<String> =
            if (phoneRegex.matches(phoneNumber)) {
                phoneNumber.valid()
            } else {
                FormError.InvalidPhoneNumber(phoneNumber).invalidNel()
            }

    private fun validateEmail(email: String): FormValidationResult<String> =
            if (email.contains("@")) {
                email.valid()
            } else {
                FormError.InvalidEmail(email).invalidNel()
            }

    private val documentRegex = "\\d{8}[a-zA-Z]{1}".toRegex()
    private val phoneRegex = "\\d{9}".toRegex()
}

data class Form(
    val firstName: String,
    val lastName: String,
    val birthday: LocalDateTime,
    val documentId: String,
    val phoneNumber: String,
    val email: String
)

sealed class FormError {
    data class EmptyFirstName(val firstName: String) : FormError()
    data class EmptyLastName(val firstName: String) : FormError()
    data class UserTooYoung(val birthday: LocalDateTime) : FormError()
    data class InvalidDocumentId(val documentId: String) : FormError()
    data class InvalidPhoneNumber(val phoneNumber: String) : FormError()
    data class InvalidEmail(val email: String) : FormError()
}