/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.application.fixture.scenarios;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifest2;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.metamodel.MetaModelService4;

import domainapp.modules.simple.dom.planta.Planta;
import domainapp.modules.simple.dom.planta.PlantaRepositorio;

import lombok.Getter;
import lombok.Setter;

public class PlantaCreate extends FixtureScript {

    private String nombre;
    private String provincia;
    private String cliente;

    public String getNombre() {return nombre;}
    public void setNombre(final String nombre) {this.nombre = nombre;}

    public String getProvincia() {return provincia;}
    public void setProvincia(final String provincia) {this.provincia = provincia;}

    public String getCliente() {return cliente;}
    public void setCliente(final String cliente) {this.cliente = cliente;}

    private Planta plantaObject;
    public Planta getPlantaObject() {return plantaObject;}

    @Override
    protected void execute(final ExecutionContext ec) {

        String nombre = checkParam("nombre", ec, String.class);
        String provincia = checkParam("provincia", ec, String.class);
        String cliente = checkParam("cliente", ec, String.class);

        this.plantaObject = wrap(plantaRepositorio).create(nombre, provincia, cliente);

        // also make available to UI
        ec.addResult(this, plantaObject);



        // AppManifest2 appManifest2 = metaModelService4.getAppManifest2();
        // ec.executeChild(this, appManifest2.getTeardownFixture());
        // ec.executeChild(this, appManifest2.getRefDataSetupFixture());
        // ec.executeChild(this, new SimpleObject_persona.PersistAll());
    }

    @Inject
    PlantaRepositorio plantaRepositorio;

    @Inject
    MetaModelService4 metaModelService4;
}
