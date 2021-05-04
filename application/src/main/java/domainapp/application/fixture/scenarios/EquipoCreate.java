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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import org.apache.isis.applib.AppManifest2;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.metamodel.MetaModelService4;

import domainapp.modules.simple.dom.compresor.Compresor;
import domainapp.modules.simple.dom.compresor.CompresorRepositorio;
import domainapp.modules.simple.dom.planta.Planta;
import domainapp.modules.simple.dom.planta.PlantaRepositorio;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.motor.MotorRepositorio;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import domainapp.modules.simple.dom.mantenimiento.MantenimientoRepositorio;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.mantenimiento.ETipoMantenimiento;
import domainapp.modules.simple.dom.tarea.TareaRepositorio;
import domainapp.modules.simple.dom.tarea.Tarea;

public class EquipoCreate extends FixtureScript {

    public EquipoCreate() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        Compresor compresor01 = compresorRepositorio.create("COM423",1,1,1,1,1,1,1,1);
        Compresor compresor02 = compresorRepositorio.create("COM980",2,2,2,2,2,2,2,2);
        Compresor compresor03 = compresorRepositorio.create("COM155",3,3,3,3,3,3,3,3);

        Motor motor01 = motorRepositorio.create("MOT117",15,17);
        Motor motor02 = motorRepositorio.create("MOT212",21,29);
        Motor motor03 = motorRepositorio.create("MOT336",30,33);

        Planta planta01 = plantaRepositorio.create("Norte", "Neuquen", "ESSO");
        Planta planta02 = plantaRepositorio.create("Sur", "Rio Negro", "YPF");
        Planta planta03 = plantaRepositorio.create("Este", "Chubut", "SHELL");

        Equipo equipo01 = equipoRepositorio.create("EQUIPO1","mod01",2500,1000, new LocalDate(2021, 7, 15),15,3600,1750,planta01,motor01,compresor01);
        Equipo equipo02 = equipoRepositorio.create("EQUIPO2","mod02",1750,1000, new LocalDate(2021, 3, 11),15,3600,1750,planta01,motor02,compresor02);
        Equipo equipo03 = equipoRepositorio.create("EQUIPO3","mod03",3800,1000, new LocalDate(2021, 9, 6),15,3600,1750,planta01,motor03,compresor03);

        Mantenimiento mantenimiento01 = mantenimientoRepositorio.create("Manten01", ETipoMantenimiento.COMPRESOR,equipo01);


        Tarea tarea01 = tareaRepositorio.create("Tarea 01","Controlar Aceite", mantenimiento01);
        Tarea tarea02 = tareaRepositorio.create("Tarea 02","Controlar Humos de Escape", mantenimiento01);
        Tarea tarea03 = tareaRepositorio.create("Tarea 03","Limpiar Salida", mantenimiento01);
        Tarea tarea04 = tareaRepositorio.create("Tarea 04","Desarmar", mantenimiento01);

    }

    @javax.inject.Inject
    private CompresorRepositorio compresorRepositorio;

    @javax.inject.Inject
    private PlantaRepositorio plantaRepositorio;

    @javax.inject.Inject
    private MotorRepositorio motorRepositorio;

    @javax.inject.Inject
    private EquipoRepositorio equipoRepositorio;

    @javax.inject.Inject
    private MantenimientoRepositorio mantenimientoRepositorio;

    @javax.inject.Inject
    private TareaRepositorio tareaRepositorio;

    @Inject
    MetaModelService4 metaModelService4;
}
