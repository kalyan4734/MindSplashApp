package com.mindsplash.network.locakstore.serializers

import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.mindsplash.StudentProto
import java.io.InputStream
import java.io.OutputStream

object StudentSerializer: Serializer<StudentProto> {
    override val defaultValue: StudentProto
        get() = StudentProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): StudentProto {
        return try {
            StudentProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            exception.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: StudentProto, output: OutputStream) {
        t.writeTo(output)
    }
}