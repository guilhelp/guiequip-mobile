import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiequip.data.Departamento
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DepartamentosViewModel : ViewModel() {

    private val _departamentos = MutableStateFlow<List<Departamento>>(emptyList())
    val departamentos: StateFlow<List<Departamento>> get() = _departamentos

    private val db = FirebaseFirestore.getInstance()

    fun fetchDepartamentos() {
        viewModelScope.launch {
            try {
                Log.d("DepartamentosViewModel", "Buscando departamentos...") // Log para saber que a busca foi iniciada
                val departamentosList = db.collection("departamentos")
                    .get()
                    .await()
                    .documents
                    .mapNotNull {
                        val departamento = it.toObject<Departamento>()
                        departamento?.copy(id = it.id)
                    }

                Log.d("DepartamentosViewModel", "Departamentos recebidos: ${departamentosList.size}") // Log para mostrar a quantidade de departamentos recebidos
                _departamentos.value = departamentosList
            } catch (e: Exception) {
                // Tratar erro
                Log.e("DepartamentosViewModel", "Erro ao buscar departamentos: ${e.message}", e) // Log de erro
            }
        }
    }

    fun deleteDepartamento(departamentoId: String) {
        viewModelScope.launch {
            try {
                Log.d("DepartamentosViewModel", "Deletando departamento com ID: $departamentoId")

                if (departamentoId.isNotEmpty()) {
                    val usuariosRef = db.collection("colaboradores")
                        .whereEqualTo("departamentoId", departamentoId)
                        .get()
                        .await()

                    usuariosRef.forEach { usuarioDocument ->
                        usuarioDocument.reference.delete().await()
                        Log.d("DepartamentosViewModel", "Usuário excluído: ${usuarioDocument.id}")
                    }

                    val equipamentosRef = db.collection("equipamentos")
                        .whereEqualTo("departamentoId", departamentoId)
                        .get()
                        .await()

                    equipamentosRef.forEach { equipamentoDocument ->
                        equipamentoDocument.reference.delete().await()
                        Log.d("DepartamentosViewModel", "Equipamento excluído: ${equipamentoDocument.id}")
                    }

                    val departamentoRef = db.collection("departamentos").document(departamentoId)
                    departamentoRef.delete().await()

                    Log.d("DepartamentosViewModel", "Departamento excluído com sucesso")

                    fetchDepartamentos()
                } else {
                    Log.e("DepartamentosViewModel", "ID do departamento inválido")
                }
            } catch (e: Exception) {
                Log.e("DepartamentosViewModel", "Erro ao deletar departamento: ${e.message}", e)
            }
        }
    }

    fun atualizarDepartamento(departamentoId: String, nome: String, descricao: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("DepartamentosViewModel", "Atualizando departamento com ID: $departamentoId")
                val departamento = hashMapOf(
                    "nome" to nome,
                    "descricao" to descricao
                )

                db.collection("departamentos")
                    .document(departamentoId)
                    .set(departamento)
                    .await()

                Log.d("DepartamentosViewModel", "Departamento atualizado com sucesso")
                onSuccess()
            } catch (e: Exception) {
                Log.e("DepartamentosViewModel", "Erro ao atualizar departamento: ${e.message}")
                onError(e)
            }
        }
    }

    fun buscarDepartamentoPorId(departamentoId: String, onSuccess: (Departamento) -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                val departamentoRef = db.collection("departamentos").document(departamentoId)
                val departamentoSnapshot = departamentoRef.get().await()

                if (departamentoSnapshot.exists()) {
                    val departamento = departamentoSnapshot.toObject<Departamento>()
                    departamento?.id = departamentoSnapshot.id // Adiciona o ID
                    departamento?.let { onSuccess(it) }
                } else {
                    Log.e("DepartamentosViewModel", "Departamento não encontrado")
                    onError(Exception("Departamento não encontrado"))
                }
            } catch (e: Exception) {
                Log.e("DepartamentosViewModel", "Erro ao buscar departamento: ${e.message}", e)
                onError(e)
            }
        }
    }





}
