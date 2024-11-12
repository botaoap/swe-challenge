import com.gabrielbotao.swechallenge.data.remote.response.ProductsResponse
import com.gabrielbotao.swechallenge.domain.mapper.ProductsMapper
import com.gabrielbotao.swechallenge.domain.model.ProductsModel
import com.gabrielbotao.swechallenge.domain.repository.Repository
import com.gabrielbotao.swechallenge.domain.usecase.GetProductsUseCaseImpl
import com.gabrielbotao.swechallenge.domain.usecase.ProductsState
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsUseCaseImplTest {

    private lateinit var getProductsUseCase: GetProductsUseCaseImpl
    private val repository: Repository = mockk()
    private val mapper: ProductsMapper = mockk()
    private val coroutineDispatcher = Dispatchers.Unconfined

    @Before
    fun setUp() {
        // Set the main dispatchers for test
        Dispatchers.setMain(coroutineDispatcher)
        getProductsUseCase = GetProductsUseCaseImpl(repository, mapper, coroutineDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Reset the main dispatchers
        Dispatchers.resetMain()
    }

    @Test
    fun `execute emits Loading state initially`() = runTest {
        // Arrange
        coEvery { repository.getProducts() } returns mockk()

        // Act
        val results = mutableListOf<ProductsState>()
        results.add(getProductsUseCase.execute().first())

        // Assert
        assertTrue { results.contains(ProductsState.Loading) }

        coVerify { repository.getProducts() }
    }

    /**
     * This test not working yet
     * Need to mock body correctly
     */
    @Ignore
    @Test
    fun `execute emits Success state with data when response is 200`() = runTest {
        // Arrange
        val productsResponse: ProductsResponse = mockk()
        val productsModel: List<ProductsModel> = listOf(mockk())
        val response: HttpResponse = mockk {
            every { headers } returns mockk(relaxed = true)
            every { status.value } returns 200
            coEvery { body<ProductsResponse>() } returns productsResponse
        }
        coEvery { repository.getProducts() } returns response
        every { mapper.getProducts(productsResponse) } returns productsModel

        // Act
        val results = mutableListOf<ProductsState>()
        getProductsUseCase.execute().collect { results.add(it) }

        // Assert
        assertTrue { results.contains(ProductsState.Loading) }
        assertTrue { results.contains(ProductsState.Success(productsModel)) }

        coVerify { repository.getProducts() }
    }

    @Test
    fun `execute emits Error state when response is 404`() = runTest {
        // Arrange
        val response: HttpResponse = mockk {
            every { headers } returns mockk(relaxed = true)
            every { status.value } returns 404
        }
        coEvery { repository.getProducts() } returns response

        // Act
        val results = mutableListOf<ProductsState>()
        getProductsUseCase.execute().collect { results.add(it) }

        // Assert
        assertTrue { results.contains(ProductsState.Loading) }
        assertTrue { results.contains(ProductsState.Error) }

        coVerify { repository.getProducts() }
    }

    @Test
    fun `execute emits Error state when exception is thrown`() = runTest {
        // Arrange
        coEvery { repository.getProducts() } throws Exception("Network error")

        // Act
        val results = mutableListOf<ProductsState>()
        getProductsUseCase.execute().collect { results.add(it) }

        // Assert
        assertTrue { results.contains(ProductsState.Loading) }
        assertTrue { results.contains(ProductsState.Error) }

        coVerify { repository.getProducts() }
    }
}
