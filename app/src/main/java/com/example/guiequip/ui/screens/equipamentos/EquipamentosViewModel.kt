import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiequip.data.Equipamento
import com.example.guiequip.data.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EquipamentosViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _equipamentos = MutableStateFlow<List<Equipamento>>(emptyList())
    val equipamentos: StateFlow<List<Equipamento>> get() = _equipamentos

    // Busca equipamentos pelo departamentoId
    fun fetchEquipamentos(departamentoId: String) {
        viewModelScope.launch {
            try {
                Log.d("EquipamentosViewModel", "Buscando equipamentos para departamentoId: $departamentoId")

                val equipamentosList = db.collection("equipamentos")
                    .whereEqualTo("departamentoId", departamentoId)
                    .get()
                    .await()
                    .documents
                    .mapNotNull { doc ->
                        Log.d("EquipamentosViewModel", "Documento encontrado: ${doc.id}, dados: ${doc.data}")

                        val equipamento = doc.toObject<Equipamento>()
                        equipamento?.copy(
                            id = doc.id, // Inclui o ID do documento
                            usuario = doc.get("usuario")?.let {
                                (it as? Map<*, *>)?.let { userMap ->
                                    Usuario(
                                        id = userMap["id"] as? String ?: "",
                                        nome = userMap["nome"] as? String ?: ""
                                    )
                                }
                            } ?: Usuario() // Valor padrão caso o usuário não esteja presente
                        )
                    }

                _equipamentos.value = equipamentosList
                Log.d("EquipamentosViewModel", "Equipamentos recuperados: $equipamentosList")
            } catch (e: Exception) {
                Log.e("EquipamentosViewModel", "Erro ao buscar equipamentos: ${e.message}", e)
            }
        }
    }


    // Exclui um equipamento pelo ID
    fun deleteEquipamento(equipamentoId: String) {
        viewModelScope.launch {
            try {
                db.collection("equipamentos").document(equipamentoId).delete().await()
                Log.d("EquipamentosViewModel", "Equipamento excluído: $equipamentoId")
                _equipamentos.value = _equipamentos.value.filter { it.id != equipamentoId }
            } catch (e: Exception) {
                Log.e("EquipamentosViewModel", "Erro ao excluir equipamento: ${e.message}")
            }
        }
    }

    // Busca um equipamento pelo ID
    suspend fun getEquipamentoById(equipamentoId: String): Equipamento? {
        return try {
            val doc = db.collection("equipamentos").document(equipamentoId).get().await()
            doc.toObject<Equipamento>()?.copy(
                id = doc.id,
                usuario = doc.get("usuario")?.let {
                    (it as? Map<*, *>)?.let { userMap ->
                        Usuario(
                            id = userMap["id"] as? String ?: "",
                            nome = userMap["nome"] as? String ?: ""
                        )
                    }
                } ?: Usuario()
            )
        } catch (e: Exception) {
            Log.e("EquipamentosViewModel", "Erro ao buscar equipamento: ${e.message}")
            null
        }
    }
}
