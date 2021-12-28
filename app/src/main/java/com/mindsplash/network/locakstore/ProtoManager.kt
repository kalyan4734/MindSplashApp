package com.mindsplash.network.locakstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.mindsplash.StudentProto
import com.mindsplash.network.locakstore.serializers.StudentSerializer

class ProtoManager(val context: Context) {
    private val STUDENT_DATA_STORE_FILE_NAME = "student_store.pb"

    private val Context.userPreferencesStore: DataStore<StudentProto> by dataStore(
        fileName = STUDENT_DATA_STORE_FILE_NAME,
        serializer = StudentSerializer
    )


}
