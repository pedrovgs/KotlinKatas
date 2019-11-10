package com.github.pedrovgs.formvalidator

import arrow.core.NonEmptyList
import arrow.core.invalid
import com.github.pedrovgs.formvalidation.Form
import com.github.pedrovgs.formvalidation.FormError
import com.github.pedrovgs.formvalidation.FormValidator
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.properties.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDateTime

class FormValidatorSpec : StringSpec({
    "should not validate an empty form where every field is wrong" {
        val form = Form(
                firstName = "",
                lastName = "  ",
                birthday = LocalDateTime.now(),
                documentId = "48632500",
                phoneNumber = "6799",
                email = "pedro"
        )

        val result = FormValidator.validateForm(LocalDateTime.now(), form)

        result.isInvalid.shouldBeTrue()
        result.shouldBe(
                NonEmptyList.of(
                        FormError.InvalidEmail(form.email),
                        FormError.InvalidPhoneNumber(form.phoneNumber),
                        FormError.InvalidDocumentId(form.documentId),
                        FormError.UserTooYoung(form.birthday),
                        FormError.EmptyFirstName(form.firstName),
                        FormError.EmptyLastName(form.lastName)
                ).invalid()
        )
    }

    "should not validate any form where just one field is invalid" {
        val form = Form(
                firstName = "Pedro",
                lastName = "Gómez",
                birthday = LocalDateTime.MIN,
                documentId = "48632500A",
                phoneNumber = "677673299",
                email = "p"
        )

        val result = FormValidator.validateForm(LocalDateTime.now(), form)

        result.isInvalid.shouldBeTrue()
        result.shouldBe(
                NonEmptyList.of(
                        FormError.InvalidEmail(form.email)
                ).invalid()
        )
    }

    "should validate any form where all the fields are correct" {
        val referenceDate = LocalDateTime.now()
        forAll(Generators.FormGeneartor(referenceDate)) { form ->
            FormValidator.validateForm(referenceDate, form).isValid
        }
    }
})