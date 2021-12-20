package com.hackathon.ahreview.utils

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class AudioWriterPCM(path: String?) {
    var mPath: String? = null
    var filename: String? = null
    var speechFile: FileOutputStream? = null

    // FIXME: mPath를 굳이 만들어 사용하는 이유를 모르겠다 AudioWriterPCM의 생성자로 path를 val 키워드 붙여서 변수로 사용하면 되는데
    init {
        mPath = path
    }

    // FIXME: 매개변수로 값이 언제나 Null이 들어올 수 있다는 것을 가정하고 코딩을 해야 한다, Nullable로 처리를 하고 그에 맞게 Null 예외처리 코드 필요
    // FIXME: !를 사용하는 것보다 .not() 코틀린 확장 함수를 쓰는게 좋다

    fun open(sessionId: String) {
        val directory = File(mPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        // FIXME: 현재는 Java느낌인데 $ 키워드로 수정해라
        filename = directory.toString() + "/" + sessionId + ".pcm"
        try {
            speechFile = FileOutputStream(File(filename))
        } catch (e: FileNotFoundException) {
            System.err.println("Can't open file : $filename")
            speechFile = null
        }
    }

    // TODO: 전체적으로 Log가 존재하지 않는다, 어떤 함수나 기능이 동작되면 로그를 추가해서 Logcat에 들어갔을 때 어떻게 상황이 흘러가는지 볼 수 있도록 해라

    fun close() {
        if (speechFile == null) return
        try {
            speechFile!!.close()
        } catch (e: IOException) {
            System.err.println("Can't close file : $filename")
        }
    }

    fun write(data: ShortArray) {
        if (speechFile == null) return
        val buffer: ByteBuffer = ByteBuffer.allocate(data.size * 2)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        for (i in data.indices) {
            buffer.putShort(data[i])
        }
        buffer.flip()
        try {
            speechFile!!.write(buffer.array())
        } catch (e: IOException) {
            System.err.println("Can't write file : $filename")
        }
    }
}