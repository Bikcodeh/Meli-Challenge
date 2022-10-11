package com.bikcodeh.melichallenge.domain.usecase

import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import javax.inject.Inject

class GetProductDescriptionUseCase @Inject constructor(
    private val meliRepository: MeliRepository
) {
    suspend operator fun invoke(productId: String) = meliRepository.getProductDescription(productId)
}