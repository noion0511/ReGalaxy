package com.ssafy.phonesin

import android.app.Application
import android.content.Context
import android.hardware.ConsumerIrManager
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.ssafy.phonesin.ui.module.remote.IrRemoteFragment
import com.ssafy.phonesin.ui.module.remote.IrRemoteFragment.Companion.SAMSUNG_BIT_MARK
import com.ssafy.phonesin.ui.module.remote.IrRemoteFragment.Companion.SAMSUNG_HDR_MARK
import com.ssafy.phonesin.ui.module.remote.IrRemoteFragment.Companion.SAMSUNG_HDR_SPACE
import com.ssafy.phonesin.ui.module.remote.IrRemoteFragment.Companion.SAMSUNG_MIN_GAP
import com.ssafy.phonesin.ui.module.remote.IrRemoteFragment.Companion.SAMSUNG_ONE_SPACE
import com.ssafy.phonesin.ui.module.remote.IrRemoteFragment.Companion.SAMSUNG_ZERO_SPACE
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowActivity

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class IrRemoteFragmentTest {

    private lateinit var fragment: IrRemoteFragment
    private lateinit var irManager: ConsumerIrManager
    private lateinit var shadowActivity: ShadowActivity

    @Before
    fun setup() {
        fragment = IrRemoteFragment()
        irManager = mock(ConsumerIrManager::class.java)

        val activityController = Robolectric.buildActivity(AppCompatActivity::class.java)
            .create()
            .start()
            .resume()

        shadowActivity = Shadows.shadowOf(activityController.get())

        activityController.get()
            .supportFragmentManager.beginTransaction()
            .add(fragment, null)
            .commitNow()

        // Context를 override하여 IR 서비스를 제공
        val context = ApplicationProvider.getApplicationContext() as Application
        `when`(context.getSystemService(Context.CONSUMER_IR_SERVICE)).thenReturn(irManager)
    }

    @Test
    fun testHasIrEmitter() {
        `when`(irManager.hasIrEmitter()).thenReturn(true)
        fragment.init()

        // 센서가 있을 때의 로직을 여기에 검증
        // 아직 센서가 있을 때, 처리는 없음
    }

    @Test
    fun testNoIrEmitter() {
        `when`(irManager.hasIrEmitter()).thenReturn(false)
        fragment.init()

        //센서가 없으면 알림 다이얼로그 띄우기
        verify(fragment).showNoIREmitterDialog()
    }

    @Test
    fun testCreateIrPattern() {
        // 테스트 값으로 1번 버튼 코드 지정
        val hexCode: Long = 0xE0E020DF

        // 패턴 리스트 생성
        val pattern = mutableListOf(
            SAMSUNG_HDR_MARK,
            SAMSUNG_HDR_SPACE
        )

        // Data 비트 추가
        for (i in 31 downTo 0) {
            pattern.add(SAMSUNG_BIT_MARK)
            pattern.add(
                if ((hexCode shr i and 1L) == 1L) {
                    SAMSUNG_ONE_SPACE
                } else {
                    SAMSUNG_ZERO_SPACE
                }
            )
        }

        // 푸터 추가
        pattern.add(SAMSUNG_BIT_MARK)
        pattern.add(SAMSUNG_MIN_GAP)

        // 실제 결과 생성
        val actualPattern = fragment.createIrPattern(hexCode)

        // 예상 결과와 실제 결과 비교
        assertArrayEquals(pattern.toIntArray(), actualPattern)
    }
}
