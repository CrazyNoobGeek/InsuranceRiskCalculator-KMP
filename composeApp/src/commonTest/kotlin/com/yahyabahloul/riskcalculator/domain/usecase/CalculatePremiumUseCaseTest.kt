package com.yahyabahloul.riskcalculator.domain.usecase

import com.yahyabahloul.riskcalculator.domain.model.DriverProfile
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatePremiumUseCaseTest {

    private val useCase = CalculatePremiumUseCase()

    @Test
    fun `should return YoungDriver price when license years is less than 3`() {
        // GIVEN (Un conducteur novice)
        val noviceProfile = DriverProfile(age = 20, yearsOfLicense = 1)

        // WHEN (On calcule)
        val result = useCase(noviceProfile)

        // THEN (On doit avoir le tarif Jeune)
        assertEquals(expected = PremiumResult.YoungDriver, actual = result)
    }

    @Test
    fun `should return Experienced price when license years is 3 or more`() {
        // GIVEN (Un conducteur expérimenté)
        val proProfile = DriverProfile(age = 30, yearsOfLicense = 5)

        // WHEN (On calcule)
        val result = useCase(proProfile)

        // THEN (On doit avoir le tarif Expérimenté)
        assertEquals(expected = PremiumResult.Experienced, actual = result)
    }

    @Test
    fun `should handle limit case exactly at 3 years`() {
        // GIVEN (Pil poil 3 ans)
        val limitProfile = DriverProfile(age = 25, yearsOfLicense = 3)

        // WHEN
        val result = useCase(limitProfile)

        // THEN (3 ans = Expérimenté chez nous)
        assertEquals(expected = PremiumResult.Experienced, actual = result)
    }
}