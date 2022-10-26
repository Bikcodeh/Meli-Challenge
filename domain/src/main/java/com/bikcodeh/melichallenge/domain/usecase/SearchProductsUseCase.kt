package com.bikcodeh.melichallenge.domain.usecase

import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val meliRepository: MeliRepository
) {
    operator fun invoke(query: String) = meliRepository.searchProducts(query)
}