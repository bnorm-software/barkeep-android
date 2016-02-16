package com.bnorm.barkeep.server.msg.xml.v2;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public enum RecipeSerializer {
    instance;

    private JAXBContext context;

    RecipeSerializer() {
        try {
            this.context = JAXBContext.newInstance(Root.class.getPackage().getName());
        } catch (JAXBException e) {
            e.printStackTrace();
            this.context = null;
        }
    }

    public Root unmarshal(InputStream input) throws IOException, JAXBException {
        return (Root) context.createUnmarshaller().unmarshal(input);
    }

    public void marshal(Root data, Writer writer) throws JAXBException {
        context.createMarshaller().marshal(data, writer);
    }
}
