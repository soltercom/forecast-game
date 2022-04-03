package ru.spb.altercom.forecastgame.utils;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);

}
