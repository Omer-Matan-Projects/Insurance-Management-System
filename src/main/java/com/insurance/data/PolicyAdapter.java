package com.insurance.data;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.insurance.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Gson adapter for serializing and deserializing the abstract Policy class.
 * Stores a "type" field in JSON to determine which subclass to instantiate on read.
 */
public class PolicyAdapter extends TypeAdapter<Policy> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void write(JsonWriter out, Policy policy) throws IOException {
        out.beginObject();
        out.name("type").value(policy.getType().name());
        out.name("id").value(policy.getId());
        out.name("firstName").value(policy.getFirstName());
        out.name("lastName").value(policy.getLastName());
        out.name("date").value(policy.getDate().format(DATE_FORMAT));
        out.name("remarks").value(policy.getRemarks());
        out.endObject();
    }

    @Override
    public Policy read(JsonReader in) throws IOException {
        String type = null;
        String id = null;
        String firstName = null;
        String lastName = null;
        String dateStr = null;
        String remarks = null;

        in.beginObject();
        while (in.hasNext()) {
            String fieldName = in.nextName();
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                continue;
            }


            switch (fieldName) {
                case "type":
                    type = in.nextString();
                    break;
                case "id":
                    id = in.nextString();
                    break;
                case "firstName":
                    firstName = in.nextString();
                    break;
                case "lastName":
                    lastName = in.nextString();
                    break;
                case "date":
                    dateStr = in.nextString();
                    break;
                case "remarks":
                    remarks = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        // Convert string data to objects
        LocalDate date = LocalDate.parse(dateStr, DATE_FORMAT);
        InsuranceType insuranceType = InsuranceType.valueOf(type);


        switch (insuranceType) {
            case CAR:
                return new CarInsurance(id, firstName, lastName, date, remarks);
            case APARTMENT:
                return new ApartmentInsurance(id, firstName, lastName, date, remarks);
            case LIFE:
                return new LifeInsurance(id, firstName, lastName, date, remarks);
            case HEALTH:
                return new HealthInsurance(id, firstName, lastName, date, remarks);
            default:
                throw new IOException("Unknown insurance type: " + type);
        }
    }
}