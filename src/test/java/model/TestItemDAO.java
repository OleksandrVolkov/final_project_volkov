package model;

import model.dao.FeedbackDAO;
import model.dao.ItemDAO;
import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.entity.Item;
import model.entity.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class TestItemDAO {
    private ItemDAO itemDAO;
    private RequestDAO requestDAO;

    @Parameterized.Parameter
    public Item item;

    @Parameterized.Parameter(1)
    public String nameUpdate;

    @Parameterized.Parameter(2)
    public String infoUpdate;

    @Parameterized.Parameter(3)
    public Request request;

    @Parameterized.Parameter(4)
    public Item items;


    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList( new Object[][]{
                {
                        new Item("newIt", "Just info"),
                        "lsakdnad",
                        "safj0sa9fja",
                        new Request("sadpdsa", "is being seen", 4),
                        new Item("isa", "sapdj")
                },
                {
                        new Item("Item", "New Info"),
                        "pojsadpoa",
                        "dnsaoisad",
                        new Request("oiashas", "is being seen", 5),
                        new Item("lknasd", "isad")
                }
                }
        );
    }


    @Before
    public void init(){
        requestDAO = new RequestDAO(ConnectionManager.getConnection());
    }

    @Test
    public void testCreate(){
        itemDAO = new ItemDAO(ConnectionManager.getConnection());
        itemDAO.create(item);
        Item curItem = itemDAO.findEntityById(item.getId());
        assertEquals(item, curItem);
        itemDAO.delete(item.getId());
    }

    @Test
    public void testDelete(){
        itemDAO = new ItemDAO(ConnectionManager.getConnection());
        itemDAO.create(item);
        itemDAO.delete(item.getId());
        assertNull(itemDAO.findEntityById(item.getId()));
    }

    @Test
    public void testUpdate(){
        itemDAO = new ItemDAO(ConnectionManager.getConnection());
//        String curName = "lsakdnad";
//        String curInfo = "safj0sa9fja";
        itemDAO.create(item);
        item.setInfo(infoUpdate);
        item.setName(nameUpdate);
        itemDAO.update(item, item.getId());
        Item itemTemp = itemDAO.findEntityById(item.getId());
        assertEquals(item, itemTemp);
        itemDAO.delete(item.getId());
    }

    @Test
    public void testFindEntityById(){
        itemDAO = new ItemDAO(ConnectionManager.getConnection());
        itemDAO.create(item);
        Item tempItem = itemDAO.findEntityById(item.getId());
        assertEquals(item, tempItem);
        itemDAO.delete(item.getId());
    }

    @Test
    public void testGetLastInsertedItemIndex(){
        itemDAO = new ItemDAO(ConnectionManager.getConnection());
        itemDAO.create(item);
        Integer itemId = itemDAO.getLastInsertedItemIndex();
        Item itemTemp = itemDAO.findEntityById(item.getId());
        assertEquals(item.getId(), itemId, itemTemp.getId());
        itemDAO.delete(item.getId());
    }

//    @Test
//    public void testGetItemsByRequest(){
//        itemDAO = new ItemDAO(ConnectionManager.getConnection());
//        for(int i = 0;i<items.size(); i++)
//            itemDAO.create(items.get(i));
//
//        request_actions.setItems(items);
//        requestDAO.create(request_actions);
//
//        List<Item> itemsVal = itemDAO.getItemsByRequest(request_actions);
//        requestDAO.delete(request_actions.getId());
//        assertEquals(items, itemsVal);
//        for(int i = 0;i<items.size(); i++)
//            itemDAO.delete(items.get(i).getId());
//    }
}
