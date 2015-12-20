package lms_rozier_convers.core.tests;

import lms_rozier_convers.core.geometry.Cuboid;
import lms_rozier_convers.core.items.Book;
import lms_rozier_convers.core.items.LibraryItem;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 25/11/2015.
 * A test for the Cuboid class
 */
public class CuboidTest {

    /**
     * Tests whether the given Cuboid can fit in another given cuboid
     * @throws Exception
     */
    @Test
    public void testCanFit() throws Exception {

        Book book = new Book(new Cuboid(5, 4, 7));

        Cuboid space1 = new Cuboid(5, 4, 7); //Has enough space
        Cuboid space2 = new Cuboid(5, 3, 7); //Has not enough space

        assertTrue(book.getShape().canFit(space1));
        assertFalse(book.getShape().canFit(space2));


    }

    @Test
    public void testGetVolume() throws Exception {
        LibraryItem book = new Book(new Cuboid(1, 2, 3));
        double volume = book.getShape().getVolume();
        assertEquals(6.0,volume,0.01);
    }


}