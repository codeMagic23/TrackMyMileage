package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Once you have your model created, use this class to generate entities and DAOs.
 *
 * @author Markus
 */

public class GreenDaoGenerator {

    /**
     * Generates entities and DAOs for the example project DaoExample.
     *
     * Run it as a Java application (not Android).
     *
     * @author Markus
     */

        public static void main(String[] args) throws Exception {
            Schema schema = new Schema(1, "com.codemagic.TrackMyMileage.database.dao");

            addVehicle(schema);
            addFillLog(schema);

            new DaoGenerator().generateAll(schema, "app/src/main/java");
        }

        // for reference only--not being used
        private static void addNote(Schema schema) {
            Entity note = schema.addEntity("Note");
            note.addIntProperty("Id");
            note.addStringProperty("text").notNull();
            note.addStringProperty("comment");
            note.addDateProperty("date");
        }

        private static void addVehicle(Schema schema) {
            Entity vehicle = schema.addEntity("Vehicle");
            vehicle.addIdProperty().autoincrement();
            vehicle.addStringProperty("vehicleName").notNull();
            vehicle.addStringProperty("makeModel").notNull();
            vehicle.addIntProperty("year");
            vehicle.addStringProperty("engineType");
        }

        private static void addFillLog(Schema schema) {
             Entity fillLog = schema.addEntity("FillLog");
             fillLog.addDateProperty("fillDate").notNull();
             fillLog.addDoubleProperty("gallons").notNull();
             fillLog.addDoubleProperty("pricePerGallon");
             fillLog.addLongProperty("curMiles").notNull();
             fillLog.addStringProperty("vehicleName").notNull();
    }

        // for reference only--not being used
        private static void addCustomerOrder(Schema schema) {
            Entity customer = schema.addEntity("Customer");
            customer.addIdProperty();
            customer.addStringProperty("name").notNull();

            Entity order = schema.addEntity("Order");
            order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
            order.addIdProperty();
            Property orderDate = order.addDateProperty("date").getProperty();
            Property customerId = order.addLongProperty("customerId").notNull().getProperty();
            order.addToOne(customer, customerId);

            ToMany customerToOrders = customer.addToMany(order, customerId);
            customerToOrders.setName("orders");
            customerToOrders.orderAsc(orderDate);
        }

    }

