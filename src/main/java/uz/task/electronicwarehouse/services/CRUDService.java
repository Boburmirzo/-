package uz.task.electronicwarehouse.services;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface CRUDService<Form, QueryParam> {
    Map<QueryParam, Form> listAll();

    Form findOne(QueryParam query) throws NoSuchElementException;

    Form saveOrUpdate(Form form);

    void delete(QueryParam query);
}
