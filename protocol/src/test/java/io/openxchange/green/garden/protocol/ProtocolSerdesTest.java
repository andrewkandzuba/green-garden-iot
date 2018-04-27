package io.openxchange.green.garden.protocol;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class ProtocolSerdesTest {

    @Test
    public void emptyMessage() throws IOException {

        ProtocolFacrory.Device device = ProtocolFacrory.Device.newBuilder()
                .setId(1)
                .build();
        ProtocolFacrory.Message message = ProtocolFacrory.Message.newBuilder()
                .setCorrelationId(device.getClass().getSimpleName())
                .setDevice(device)
                .build();

        byte[] envelopeBuffer = serialize(message);
        assertNotNull(envelopeBuffer);

        ProtocolFacrory.Message unpacked = ProtocolFacrory.Message.parseFrom(envelopeBuffer);

        assertEquals(message, unpacked);
        assertEquals(device, unpacked.getDevice());

        assertFalse(unpacked.hasTemperature());
        assertFalse(unpacked.hasHumidity());
        assertFalse(unpacked.hasWind());
    }

    @Test
    public void temperatureMessage() throws IOException {

        ProtocolFacrory.Temperature temperature = ProtocolFacrory.Temperature.newBuilder()
                .setScale(ProtocolFacrory.Temperature.Scale.FAHRENHEIT)
                .setDegree(86)
                .build();
        ProtocolFacrory.Device device = ProtocolFacrory.Device.newBuilder()
                .setId(1)
                .build();
        ProtocolFacrory.Message message = ProtocolFacrory.Message.newBuilder()
                .setCorrelationId(device.getClass().getSimpleName())
                .setDevice(device)
                .setTemperature(temperature)
                .build();

        byte[] envelopeBuffer = serialize(message);
        assertNotNull(envelopeBuffer);

        ProtocolFacrory.Message unpacked = ProtocolFacrory.Message.parseFrom(envelopeBuffer);

        assertEquals(message, unpacked);
        assertEquals(device, unpacked.getDevice());

        assertEquals(temperature, unpacked.getTemperature());
        assertFalse(unpacked.hasHumidity());
        assertFalse(unpacked.hasWind());
    }

    @Test
    public void humidityMessage() throws IOException {

        ProtocolFacrory.Humidity humidity = ProtocolFacrory.Humidity.newBuilder()
                .setPecent(39)
                .build();
        ProtocolFacrory.Device device = ProtocolFacrory.Device.newBuilder()
                .setId(1)
                .build();
        ProtocolFacrory.Message message = ProtocolFacrory.Message.newBuilder()
                .setCorrelationId(device.getClass().getSimpleName())
                .setDevice(device)
                .setHumidity(humidity)
                .build();

        byte[] envelopeBuffer = serialize(message);
        assertNotNull(envelopeBuffer);

        ProtocolFacrory.Message unpacked = ProtocolFacrory.Message.parseFrom(envelopeBuffer);

        assertEquals(message, unpacked);
        assertEquals(device, unpacked.getDevice());

        assertFalse(unpacked.hasTemperature());
        assertEquals(humidity, message.getHumidity());
        assertFalse(unpacked.hasWind());
    }

    @Test
    public void windMessage() throws IOException {

        ProtocolFacrory.Wind wind = ProtocolFacrory.Wind.newBuilder()
                .setDirection(90)
                .setScale(ProtocolFacrory.Wind.Scale.MPH)
                .setSpeed(12)
                .build();
        ProtocolFacrory.Device device = ProtocolFacrory.Device.newBuilder()
                .setId(1)
                .build();
        ProtocolFacrory.Message message = ProtocolFacrory.Message.newBuilder()
                .setCorrelationId(device.getClass().getSimpleName())
                .setDevice(device)
                .setWind(wind)
                .build();

        byte[] envelopeBuffer = serialize(message);
        assertNotNull(envelopeBuffer);

        ProtocolFacrory.Message unpacked = ProtocolFacrory.Message.parseFrom(envelopeBuffer);

        assertEquals(message, unpacked);
        assertEquals(device, unpacked.getDevice());

        assertFalse(unpacked.hasTemperature());
        assertFalse(unpacked.hasHumidity());
        assertEquals(wind, unpacked.getWind());
    }

    @Test
    public void resetOneOfFields() {
        ProtocolFacrory.Temperature temperature = ProtocolFacrory.Temperature.newBuilder()
                .setScale(ProtocolFacrory.Temperature.Scale.FAHRENHEIT)
                .setDegree(86)
                .build();
        ProtocolFacrory.Wind wind = ProtocolFacrory.Wind.newBuilder()
                .setDirection(90)
                .setScale(ProtocolFacrory.Wind.Scale.MPH)
                .setSpeed(12)
                .build();
        ProtocolFacrory.Device device = ProtocolFacrory.Device.newBuilder()
                .setId(1)
                .build();

        ProtocolFacrory.Message message = ProtocolFacrory.Message.newBuilder()
                .setCorrelationId(device.getClass().getSimpleName())
                .setDevice(device)
                .setTemperature(temperature)
                .build();

        assertTrue(message.hasDevice());
        assertTrue(message.hasTemperature());
        assertFalse(message.hasHumidity());
        assertFalse(message.hasWind());

        message = ProtocolFacrory.Message.newBuilder()
                .setCorrelationId(device.getClass().getSimpleName())
                .setDevice(device)
                .setTemperature(temperature)
                .setWind(wind)
                .build();

        assertTrue(message.hasDevice());
        assertFalse(message.hasTemperature());
        assertFalse(message.hasHumidity());
        assertTrue(message.hasWind());
    }

    @Test
    public void serializeAllFieldsNotSet() throws IOException {
        ProtocolFacrory.Message message = ProtocolFacrory.Message.newBuilder().build();

        byte[] envelopeBuffer = serialize(message);
        assertNotNull(envelopeBuffer);

        ProtocolFacrory.Message unpacked = ProtocolFacrory.Message.parseFrom(envelopeBuffer);

        assertEquals(message, unpacked);

        assertFalse(message.hasDevice());
        assertFalse(message.hasTemperature());
        assertFalse(message.hasHumidity());
        assertFalse(message.hasWind());
    }

    @Test(expected = IOException.class)
    public void wrongBuffer() throws InvalidProtocolBufferException {
        byte[] envelopeBuffer = "This is not a protobuf".getBytes(Charset.defaultCharset());
        ProtocolFacrory.Message.parseFrom(envelopeBuffer);
    }

    private byte[] serialize(GeneratedMessageV3 message) throws IOException {
        byte[] byteBuffer;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            message.writeTo(baos);
            byteBuffer = baos.toByteArray();
        }
        return byteBuffer;
    }
}
