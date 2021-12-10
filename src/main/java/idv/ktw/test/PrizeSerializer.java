package idv.ktw.test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PrizeSerializer extends StdSerializer{
	public PrizeSerializer() {
        super(Prize.class);
    }

    public PrizeSerializer(Class t) {
        super(t);
    }
    
    public void serialize(Prize prize, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
        
    }

	public void serialize(Object prize, JsonGenerator generator, SerializerProvider provider) throws IOException {
		Prize p = (Prize) prize;
		generator.writeStartObject();
        generator.writeFieldName("name");
        generator.writeString(p.getName());
        generator.writeFieldName("money");
        generator.writeNumber(p.getMoney());
        generator.writeEndObject();
	}
}
