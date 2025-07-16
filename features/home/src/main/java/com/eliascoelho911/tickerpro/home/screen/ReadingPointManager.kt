package com.eliascoelho911.tickerpro.home.screen

import com.eliascoelho911.book.content.Page
import com.eliascoelho911.book.content.TextBlock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Gerencia o ponto atual de leitura, mantendo a página corrente e
 * o bloco de texto que está sendo lido. Além disso, oferece operações
 * para iniciar e pausar a "leitura" (playback) dos blocos de texto.
 */
class ReadingPointManager(
    private val playbackDelayMillis: Long = 500L
) {

    // Fluxo que guarda a página atual
    private val _currentPage = MutableStateFlow<Page?>(null)
    val currentPage: StateFlow<Page?> = _currentPage.asStateFlow()

    // Fluxo que guarda o bloco de texto atual sendo lido
    private val _currentTextBlock = MutableStateFlow<TextBlock?>(null)
    val currentTextBlock: StateFlow<TextBlock?> = _currentTextBlock.asStateFlow()

    private var playbackJob: Job? = null

    /**
     * Atualiza a página atual e reseta o bloco de leitura.
     */
    fun setPage(page: Page) {
        _currentPage.value = page
        _currentTextBlock.value = null // reinicia o ponto de leitura ao mudar a página
    }

    /**
     * Inicia o playback dos blocos de texto da página atual, começando
     * a partir do bloco definido ou do início da página.
     *
     * @param scope Escopo de corrotina no qual o playback será iniciado.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun play(scope: CoroutineScope) {
        if (playbackJob?.isActive == true) return

        playbackJob = scope.launch {
            currentPage.filterNotNull().flatMapLatest { page ->
                flow {
                    // Determina o índice de início com base no bloco atual ou 0 se não houver
                    val startIndex = _currentTextBlock.value?.let { currentBlock ->
                        page.textBlocks.indexOf(currentBlock).takeIf { it >= 0 } ?: 0
                    } ?: 0

                    // Emite os blocos de texto a partir do índice determinado, com delay entre eles
                    page.textBlocks.drop(startIndex).forEach { block ->
                        emit(block)
                        delay(playbackDelayMillis)
                    }
                }
            }.collect { block ->
                _currentTextBlock.update { block }
            }
        }
    }

    /**
     * Pausa o playback, cancelando a corrotina responsável pela leitura.
     */
    fun pause() {
        playbackJob?.cancel()
        playbackJob = null
    }
}
