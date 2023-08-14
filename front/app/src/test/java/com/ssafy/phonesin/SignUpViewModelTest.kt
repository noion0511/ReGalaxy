package com.ssafy.phonesin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ssafy.phonesin.model.BaseResponse
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.model.MemberDto
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.user.UserRepository
import com.ssafy.phonesin.ui.signup.SignupViewModel
import com.ssafy.phonesin.ui.util.getOrAwaitValueTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SignUpViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository: UserRepository = Mockito.mock(UserRepository::class.java)
    private val viewModel = SignupViewModel(repository)

    @Test
    fun `회원가입 성공 테스트`() = runBlocking {
        val memberDto = MemberDto("gumiinsider@gmail.com", "", "", "")
        val expectedResponse = BaseResponse(message = "회원 가입에 성공하였습니다.", status = "200")
        `when`(repository.signup(memberDto)).thenReturn(NetworkResponse.Success(expectedResponse))

        viewModel.signup(memberDto)

        val actualResponse = viewModel.signupResponse.getOrAwaitValueTest()
        assertEquals(Event(expectedResponse), actualResponse)
    }

    @Test
    fun `이메일 인증 안한 경우, 회원가입 실패 테스트`() = runBlocking {
        val memberDto = MemberDto("", "", "", "")
        val expectedResponse = BaseResponse(message = "이메일 인증을 진행해주세요.", status = "400")
        `when`(repository.signup(memberDto)).thenReturn(NetworkResponse.Success(expectedResponse))

        viewModel.signup(memberDto)

        val actualResponse = viewModel.signupResponse.getOrAwaitValueTest()
        assertEquals(Event(expectedResponse), actualResponse)
    }

    @Test
    fun `이메일 인증번호 확인 성공 테스트`() = runBlocking {
        val emailCheck = EmailCheckRequestDto("gumiinsider@gmail.com", "824803")
        val expectedResponse = BaseResponse(message = "이메일이 성공적으로 인증되었습니다.", status = "200")
        `when`(repository.verifyEmailConfirm(emailCheck)).thenReturn(NetworkResponse.Success(expectedResponse))

        viewModel.verifyEmailConfirm(emailCheck)

        val actualResponse = viewModel.emailCheck.getOrAwaitValueTest()
        assertEquals(Event(expectedResponse.message), actualResponse)
    }

    @Test
    fun `인증번호가 일치하지 않을 때, 이메일 인증번호 확인 실패 테스트`() = runBlocking {
        val emailCheck = EmailCheckRequestDto("gumiinsider@gmail.com", "824802")
        val expectedResponse = BaseResponse(message = "인증 코드가 일치하지 않습니다.", status = "400")
        `when`(repository.verifyEmailConfirm(emailCheck)).thenReturn(NetworkResponse.Success(expectedResponse))

        viewModel.verifyEmailConfirm(emailCheck)

        val actualResponse = viewModel.emailCheck.getOrAwaitValueTest()
        assertEquals(Event(expectedResponse.message), actualResponse)
    }

    @Test
    fun `이메일 인증번호 전송 성공 테스트`() = runBlocking {
        val email = EmailRequestDto("gumiinsider@gmail.com")
        val expectedResponse = BaseResponse(message = "이메일 인증 코드가 발송되었습니다.", status = "200")
        `when`(repository.verifyEmail(email)).thenReturn(NetworkResponse.Success(expectedResponse))

        viewModel.verifyEmail(email)

        val actualResponse = viewModel.errorMsg.getOrAwaitValueTest()
        assertEquals(Event(expectedResponse.message), actualResponse)
    }

    @Test
    fun `이메일 형식을 지키지 않았을 때, 인증번호 전송 실패 테스트`() = runBlocking {
        val email = EmailRequestDto("")
        val expectedResponse = BaseResponse(message = "Could not parse mail; nested exception is javax.mail.internet.AddressException: Illegal address in string ``''", status = "409")
        `when`(repository.verifyEmail(email)).thenReturn(NetworkResponse.Success(expectedResponse))

        viewModel.verifyEmail(email)

        val actualResponse = viewModel.errorMsg.getOrAwaitValueTest()
        assertEquals(Event(expectedResponse.message), actualResponse)
    }

    @Test
    fun `이미 가입된 이메일일 때, 인증번호 전송 실패 테스트`() = runBlocking {
        val email = EmailRequestDto("noion0511@gmail.com")
        val expectedResponse = BaseResponse(message = "이미 존재하는 이메일입니다.", status = "409")
        `when`(repository.verifyEmail(email)).thenReturn(NetworkResponse.Success(expectedResponse))

        viewModel.verifyEmail(email)

        val actualResponse = viewModel.errorMsg.getOrAwaitValueTest()
        assertEquals(Event(expectedResponse.message), actualResponse)
    }
}