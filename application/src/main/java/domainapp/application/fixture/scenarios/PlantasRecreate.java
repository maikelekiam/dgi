package domainapp.application.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import lombok.Getter;

public class PlantasRecreate extends FixtureScript {

    public final List<String> nombres = Collections.unmodifiableList(Arrays.asList("Norte", "Sur", "Oeste"));
    public final List<String> provincias = Collections.unmodifiableList(Arrays.asList("Neuquen", "Rio Negro", "Chubut"));
    public final List<String> clientes = Collections.unmodifiableList(Arrays.asList("Schlumberger", "YPF", "NABORS"));

    public PlantasRecreate() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new PlantaTearDown());

        for (int i = 0; i < nombres.size(); i++) {
            final PlantaCreate fs = new PlantaCreate();
            fs.setNombre(nombres.get(i));
            fs.setProvincia(provincias.get(i));
            fs.setCliente(clientes.get(i));

            ec.executeChild(this, fs.getNombre(), fs);
            //empresaObjects.add(fs.getEmpresaObject());
        }
    }

}
