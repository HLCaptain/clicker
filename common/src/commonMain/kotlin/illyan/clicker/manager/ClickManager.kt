package illyan.clicker.manager

import illyan.clicker.repository.ClickRepository
import org.koin.core.annotation.Factory

@Factory
class ClickManager(
    private val clickRepository: ClickRepository
) {
    suspend fun incrementClickCount() {
        clickRepository.incrementClickCount()
    }

    suspend fun resetClickCount() {
        clickRepository.resetClickCount()
    }

    val clickCount = clickRepository.clickCount
}