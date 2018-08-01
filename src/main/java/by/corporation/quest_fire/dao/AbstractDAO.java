package by.corporation.quest_fire.dao;

import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.pool.PooledConnection;


public abstract class AbstractDAO {

    protected PooledConnection connection;


    public AbstractDAO(PooledConnection connection) {
        this.connection = connection;
    }


    public PooledConnection getConnection() {
        return ConnectionPool.getInstance().getConnection();
    }


}