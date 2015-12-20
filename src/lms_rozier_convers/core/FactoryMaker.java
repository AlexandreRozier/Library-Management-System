package lms_rozier_convers.core;

import lms_rozier_convers.core.card.CardFactory;
import lms_rozier_convers.core.items.ItemFactory;

/**
 * 16/11/2015.
 * This class returns the asked factory
 */
public abstract class FactoryMaker {
    /**
     * Creates an AbstractFactory of the desired type
     * @param factoryName
     * @return AbstractFactory
     */
    public static  AbstractFactory createFactory(String factoryName)
    {
        //factory is the object returned by this method. It is either null or an AbstractFactory instance.
        AbstractFactory factory = null;

        //The switch loop charged to create the asked factory.
        switch (factoryName){
            case "cardFactory":
                factory = new CardFactory();
                break;
            case "itemFactory":
                factory = new ItemFactory();
                break;
            default:
                break;
        }

        return factory;
    }
}
