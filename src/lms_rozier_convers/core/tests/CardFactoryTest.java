package lms_rozier_convers.core.tests;

import lms_rozier_convers.core.AbstractFactory;
import lms_rozier_convers.core.FactoryMaker;
import lms_rozier_convers.core.card.Card;
import lms_rozier_convers.core.card.CardFactory;
import org.junit.Test;
import org.w3c.dom.views.AbstractView;

import java.lang.reflect.Member;

import static org.junit.Assert.*;

/**
 * 17/11/2015.
 * Tests the CardFactory
 */
public class CardFactoryTest {

    @Test
    public void testCreateCard() throws Exception {
        AbstractFactory cardFactory = FactoryMaker.createFactory("cardFactory");
        Card memberCard = ((CardFactory)cardFactory).create("Frequent");
        assertEquals(30,memberCard.getFineAmount(),0.1);
    }
}