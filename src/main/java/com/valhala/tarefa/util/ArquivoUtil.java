package com.valhala.tarefa.util;

import com.valhala.tarefa.exceptions.ArquivoUtilException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classe utilitaria para operações em arquivos.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 27/06/2014
 */
public abstract class ArquivoUtil {

    /**
     * Método utilizado para ler um arquivo e devolver o Stream dele do mesmo.
     *
     * @param caminho
     * @param nome
     * @return
     * @throws ArquivoUtilException
     */
    public static final InputStream obterStreamDeArquivo(String caminho, String nome) throws ArquivoUtilException {
        InputStream in = null;
        FileSystem fs = FileSystems.getDefault();
        Path caminhoArquivo = fs.getPath(caminho);
        Path arquivo = caminhoArquivo.resolve(nome);
        try {
            in = Files.newInputStream(arquivo);
        } catch (IOException e) {
            throw new ArquivoUtilException(e);
        } // fim do bloco try/catch
        return in;
    } // fim do método obterStreamDeArquivo

} // fim da classe ArquivoUtil