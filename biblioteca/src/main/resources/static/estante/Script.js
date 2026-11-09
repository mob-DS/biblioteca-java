const API_URL = 'http://localhost:8080/livros';
let livrosGlobais = [];
let abaAtual = 'LENDO';

async function carregarLivros() { // função para buscar os livros cadastrados.
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error('Erro ao buscar dados da API');
        livrosGlobais = await response.json();
        renderizarEstante();
    } catch (error) {
        console.error(error);
        document.getElementById('estante-grid').innerHTML = 
            `<p style="text-align: center; color: #ef4444; grid-column: 1/-1; padding: 30px;">Erro ao conectar com o Backend Spring Boot. Verifique se a aplicação está rodando.</p>`;
    }
}

function mudarAba(aba, elementoBotao) {
    abaAtual = aba;
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    elementoBotao.classList.add('active');
    renderizarEstante();
}

function renderizarEstante() {
    const grid = document.getElementById('estante-grid');
    grid.innerHTML = '';

    const livrosFiltrados = livrosGlobais.filter(livro => {
        if (abaAtual === 'FAVORITO') {
            return livro.favorito === true;
        }
        return livro.status === abaAtual;
    });

    if (livrosFiltrados.length === 0) {
        grid.innerHTML = `<p style="text-align: center; color: #71717a; grid-column: 1/-1; padding: 40px;">Nenhuma obra encontrada nesta aba.</p>`;
        return;
    }

    livrosFiltrados.forEach(livro => {
        const isConcluido = livro.capitulosLidos >= livro.capitulosTotal && livro.capitulosTotal > 0;
        const classeProgresso = isConcluido ? 'progress-vermelha' : 'progress-verde';
        const capa = livro.capaUrl ? livro.capaUrl : 'https://via.placeholder.com/170x230?text=Sem+Capa';

        const card = document.createElement('div');
        card.className = 'card';
        card.onclick = () => abrirModalEdicao(livro);
        card.innerHTML = `
            <div class="card-img-wrapper">
                <div class="badge-tipo">${livro.tipoObra || 'OBRA'}</div>
                ${livro.favorito ? '<div class="fav-icon">⭐</div>' : ''}
                <img src="${capa}" alt="${livro.titulo}" onerror="this.src='https://via.placeholder.com/170x230?text=Erro+Imagem'">
            </div>
            <div class="badge-categoria">${livro.categoriaPrincipal || 'Geral'}</div>
            <div class="progress-bar ${classeProgresso}">
                ${livro.capitulosLidos} / ${livro.capitulosTotal}
            </div>
            <div class="card-title">${livro.titulo}</div>
        `;
        grid.appendChild(card);
    });
}

/* --- Modal Controls --- */
function abrirModal() {
    document.getElementById('modal-titulo').innerText = 'Adicionar Nova Obra';
    document.getElementById('form-livro').reset();
    document.getElementById('livro-id').value = '';
    document.getElementById('btn-deletar').style.display = 'none'; // Esconde deletar ao criar novo
    document.getElementById('dex-results').style.display = 'none';
    document.getElementById('modal-livro').style.display = 'flex';
}

function abrirModalEdicao(livro) {
    document.getElementById('modal-titulo').innerText = 'Editar Obra';
    document.getElementById('livro-id').value = livro.id;
    document.getElementById('livro-titulo').value = livro.titulo || '';
    document.getElementById('livro-autor').value = livro.autor || '';
    document.getElementById('livro-status').value = livro.status || 'LENDO';
    document.getElementById('livro-tipo').value = livro.tipoObra || 'MANHWA';
    document.getElementById('livro-categoria').value = livro.categoriaPrincipal || '';
    document.getElementById('livro-lidos').value = livro.capitulosLidos || 0;
    document.getElementById('livro-total').value = livro.capitulosTotal || 0;
    document.getElementById('livro-capa').value = livro.capaUrl || '';
    document.getElementById('livro-sinopse').value = livro.sinopse || '';
    document.getElementById('livro-favorito').checked = livro.favorito || false;
    
    document.getElementById('btn-deletar').style.display = 'block'; // Mostra o botão de excluir ao editar
    document.getElementById('dex-results').style.display = 'none';
    document.getElementById('modal-livro').style.display = 'flex';
}

function fecharModal() {
    document.getElementById('modal-livro').style.display = 'none';
}

/* --- Busca Online via Jikan API (MyAnimeList - Super Estável) --- */
async function pesquisarOnline() {
    const termo = document.getElementById('input-busca-dex').value.trim();
    const resultsBox = document.getElementById('dex-results');
    
    if (!termo) return;

    resultsBox.innerHTML = '<div class="dex-item">Buscando online...</div>';
    resultsBox.style.display = 'block';

    try {
        const response = await fetch(`https://api.jikan.moe/v4/manga?q=${encodeURIComponent(termo)}&limit=5`);
        if (!response.ok) throw new Error('Erro na requisição');

        const data = await response.json();

        if (!data.data || data.data.length === 0) {
            resultsBox.innerHTML = '<div class="dex-item">Nenhuma obra encontrada.</div>';
            return;
        }

        resultsBox.innerHTML = '';
        data.data.forEach(manga => {
            const titulo = manga.title || 'Sem Título';
            const sinopse = manga.synopsis || '';
            const capaUrl = manga.images?.jpg?.large_image_url || manga.images?.jpg?.image_url || '';
            const capitulosTotal = manga.chapters || 0;
            const autor = (manga.authors && manga.authors.length > 0) ? manga.authors[0].name : 'Desconhecido';
            
            const genres = manga.genres ? manga.genres.map(g => g.name) : [];
            const categoriaPrincipal = genres.length > 0 ? genres[0] : 'Geral';

            const itemDiv = document.createElement('div');
            itemDiv.className = 'dex-item';
            itemDiv.innerText = `${titulo} (${manga.type || 'Manga'})`;
            itemDiv.onclick = () => {
                document.getElementById('livro-titulo').value = titulo;
                document.getElementById('livro-autor').value = autor;
                document.getElementById('livro-categoria').value = categoriaPrincipal;
                document.getElementById('livro-total').value = capitulosTotal;
                document.getElementById('livro-sinopse').value = sinopse;
                if (capaUrl) document.getElementById('livro-capa').value = capaUrl;
                resultsBox.style.display = 'none';
            };
            resultsBox.appendChild(itemDiv);
        });

    } catch (error) {
        console.error(error);
        resultsBox.innerHTML = '<div class="dex-item" style="color:#ef4444;">Erro na busca. Preencha manualmente abaixo!</div>';
    }
}

/* --- Salvar ou Atualizar no Spring Boot --- */
async function salvarLivro(event) {
    event.preventDefault();

    const id = document.getElementById('livro-id').value;
    const dados = {
        titulo: document.getElementById('livro-titulo').value,
        autor: document.getElementById('livro-autor').value,
        status: document.getElementById('livro-status').value,
        tipoObra: document.getElementById('livro-tipo').value,
        categoriaPrincipal: document.getElementById('livro-categoria').value,
        capitulosLidos: parseInt(document.getElementById('livro-lidos').value) || 0,
        capitulosTotal: parseInt(document.getElementById('livro-total').value) || 0,
        capaUrl: document.getElementById('livro-capa').value,
        sinopse: document.getElementById('livro-sinopse').value,
        favorito: document.getElementById('livro-favorito').checked
    };

    const metodo = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : API_URL;

    try {
        const response = await fetch(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dados)
        });

        if (!response.ok) throw new Error('Erro ao salvar no servidor');

        fecharModal();
        carregarLivros();
    } catch (error) {
        console.error(error);
        alert('Erro ao salvar a obra. Verifique os campos.');
    }
}

/* --- Deletar Livro --- */
async function deletarLivro() {
    const id = document.getElementById('livro-id').value;
    if (!id) return;

    if (!confirm('Tem certeza que deseja excluir esta obra da sua estante?')) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Erro ao excluir obra');

        fecharModal();
        carregarLivros();
    } catch (error) {
        console.error(error);
        alert('Erro ao excluir a obra.');
    }
}

carregarLivros();