package com.ssafy.phonesin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.model.Token
import com.ssafy.phonesin.model.dto.LoginRequestDto
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.login.LoginRepository
import com.ssafy.phonesin.ui.login.LoginViewModel
import com.ssafy.phonesin.ui.util.getOrAwaitValueTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository: LoginRepository = mock(LoginRepository::class.java)
    private val viewModel = LoginViewModel(repository)


    @Test
    fun `로그인 성공 테스트`() = runBlocking {
        val loginRequest = LoginRequestDto("noion0511@gmail.com", "12345678")
        val token = Token(accessToken = "accessToken", refreshToken = "refreshToken")
        `when`(repository.login(loginRequest)).thenReturn(NetworkResponse.Success(token))

        viewModel.login(loginRequest)

        val value = viewModel.token.getOrAwaitValueTest()
        assertEquals(Event(token), value)
    }

    @Test
    fun `API 에러 테스트`() = runBlocking {
        val loginRequest = LoginRequestDto("username", "password")
        `when`(repository.login(loginRequest)).thenReturn(NetworkResponse.ApiError(ErrorResponse(status = "401", message = "이메일 또는 비밀번호가 일치하지 않습니다."), code = 401))

        viewModel.login(loginRequest)

        // _msg 값이 0과 "로그인에"로 변경되었는지 확인
        assertEquals(Event("Api 오류 : 로그인에 실패했습니다."), viewModel.errorMsg.getOrAwaitValueTest())
    }
}
