package sk.lighture.flowmanager;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import sk.lighture.flowmanager.impl.FlowManagerFactory;

import static org.junit.Assert.assertTrue;

public class FlowManagerFactoryUnitTest {

    @Test
    public void correct_privateConstructor_reflextionCreated() throws Exception {
        Constructor constructor = FlowManagerFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}