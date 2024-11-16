import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiequip.data.Colaborador
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ColaboradoresViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _colaboradores = MutableStateFlow<List<Colaborador>>(emptyList())
    val colaboradores: StateFlow<List<Colaborador>> get() = _colaboradores

    // Busca colaboradores pelo departamentoId
    fun fetchColaboradores(departamentoId: String) {
        viewModelScope.launch {
            try {
                val colaboradoresList = db.collection("colaboradores")
                    .whereEqualTo("departamentoId", departamentoId)
                    .get()
                    .await()
                    .documents
                    .mapNotNull {
                        val colaborador = it.toObject<Colaborador>()
                        colaborador?.copy(id = it.id)
                    }
                _colaboradores.value = colaboradoresList
            } catch (e: Exception) {
                Log.e("ColaboradoresViewModel", "Erro ao buscar colaboradores: ${e.message}")
            }
        }
    }

    // Exclui um colaborador pelo ID
    fun deleteColaborador(colaboradorId: String) {
        viewModelScope.launch {
            try {
                db.collection("colaboradores").document(colaboradorId).delete().await()
                Log.d("ColaboradoresViewModel", "Colaborador excluído: $colaboradorId")
                _colaboradores.value = _colaboradores.value.filter { it.id != colaboradorId }
            } catch (e: Exception) {
                Log.e("ColaboradoresViewModel", "Erro ao excluir colaborador: ${e.message}")
            }
        }
    }

    // Busca um colaborador pelo ID
    suspend fun getColaboradorById(colaboradorId: String): Colaborador? {
        return try {
            val doc = db.collection("colaboradores").document(colaboradorId).get().await()
            doc.toObject<Colaborador>()?.copy(id = doc.id)
        } catch (e: Exception) {
            Log.e("ColaboradoresViewModel", "Erro ao buscar colaborador: ${e.message}")
            null
        }
    }

    // Atualiza os dados de um colaborador
    fun updateColaborador(colaboradorId: String, nome: String, email: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                val data = mapOf("nome" to nome, "email" to email)
                db.collection("colaboradores").document(colaboradorId).set(data).await()
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

}
