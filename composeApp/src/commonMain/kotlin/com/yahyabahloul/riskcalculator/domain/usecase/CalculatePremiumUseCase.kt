package com.yahyabahloul.riskcalculator.domain.usecase

import com.yahyabahloul.riskcalculator.domain.model.DriverProfile

class CalculatePremiumUseCase {

    // On utilise "operator fun invoke" pour pouvoir appeler la classe comme une fonction
    operator fun invoke(profile: DriverProfile): PremiumResult {

        return if (profile.yearsOfLicense < 3) {
            PremiumResult.YoungDriver
        } else {
            PremiumResult.Experienced
        }
    }
}

sealed class PremiumResult(val basePrice: Double, val message: String) {
    data object YoungDriver : PremiumResult(1000.0, "Tarif Jeune Conducteur (+Surprime)")
    data object Experienced : PremiumResult(600.0, "Tarif Standard (Bonus inclus)")
}