package illyan.clicker.data.sqldelight

import app.cash.sqldelight.Query
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import illyan.clicker.db.Database
import illyan.clicker.di.flatMapLatestAsList
import illyan.clicker.di.flatMapLatestAsOne
import illyan.clicker.di.flatMapLatestAsOneOrNull
import org.koin.core.annotation.Single

// Modified from: https://github.com/cashapp/sqldelight/blob/master/sample-web/src/jsMain/kotlin/com/example/sqldelight/hockey/data/DbHelper.kt
@Single
class DatabaseHelper(
    private val databaseFlow: StateFlow<Database?>
) {
    private val mutex = Mutex()

    /**
     * Executes [block] with a [Database] instance.
     */
    suspend fun<T> withDatabase(block: suspend (Database) -> T) = mutex.withLock {
        block(databaseFlow.first { it != null }!!)
    }

    /**
     * When collected, executes [block] with a [Database] instance.
     */
    fun<T> withDatabaseResult(block: suspend (Database) -> T) = flow {
        mutex.withLock {
            emit(block(databaseFlow.first { it != null }!!))
        }
    }

    fun<T : Any> queryAsOneFlow(block: suspend (Database) -> Query<T>) = withDatabaseResult(block).flatMapLatestAsOne()
    fun<T : Any> queryAsOneOrNullFlow(block: suspend (Database) -> Query<T>) = withDatabaseResult(block).flatMapLatestAsOneOrNull()
    fun<T : Any> queryAsListFlow(block: suspend (Database) -> Query<T>) = withDatabaseResult(block).flatMapLatestAsList()
}