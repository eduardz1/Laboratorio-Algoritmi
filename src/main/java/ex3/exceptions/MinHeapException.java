package main.java.ex3.exceptions;

//TODO: Modificare le eccezioni per suddividere le classi in maniera più specifica

/**
 * Exception throwable by the MinHeap Library
 */
public class MinHeapException extends Exception {
  public MinHeapException(String message) {
    super(message);
  }
}