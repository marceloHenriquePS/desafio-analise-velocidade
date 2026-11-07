# Decisões

1. **Que situações o enunciado não cobria e o que você decidiu em cada uma?**
   1. **Como converter o arquivo**: Demorei um pouco pensando como converter, se ia para o meio bruto (o que acabou sendo) ou usaria uma biblioteca externa que encontrei em minhas pesquisas, a `OpenCSV`.
   2. **Linhas Totalmente e branco:** Por não especificar se era ou não para contabilizar as linhas em branco do arquivo decidi apenas ignorá-las.
   3. **Normalizar Placas:** ao executar achei nos resultados uma placa que tinha letras minúsculas, normalizei todas para melhorar a analise.
   4. **O limite de velocidade:** ele devia ser tratado como a própria velocidade, ou melhor, sendo > 0, justamente por que há uma divisao envolvendo ele.
   5. **Linhas com campos faltando**: tive que verificar o tamanho da lista de string que o csv gerava ao ler a linha.
   6. **Episodio com uma leitura só**: fique indeciso, mas considerei como um episodio também, com duração de 0 segundos.
   7. **Como percorrer as listas**: procurei por metodos e expressões que fossem eficientes não poluissem o código, já que não se especificava por soluções mais brutas ou eficientes.

2. **Qual foi a parte mais difícil e como você resolveu?**
   1. **Episodios** sem sombra de dúvidas, converter e gerar o ranking foi tranquilo, já dividir e construir os episódios foi complicado pois tinha muitas validações e regras, envolvendo muito pensamento lógico. A forma como percorrer e principalmente comparar os episódios foi díficil, mas, ao pesquisar achei algumas soluções que usavam `.stream()`

3. **O que você faria diferente com mais tempo?**
   1. **Testes automatizados**, um conteúdo que não possuo conhecimento profundo, seria uma ótima oportunidade de aprender.
   2. **Melhorar a interface**, a interface usa HTML puro, a forma mais básica de uma aplicação angular, deixaria mais organizado e melhoraria o desing.