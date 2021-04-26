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
package domainapp.modules.simple.dom.equipo;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.planta.Planta;
import domainapp.modules.simple.dom.planta.PlantaRepositorio;
import lombok.AccessLevel;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.joda.time.LocalDate;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Equipo_denominacion_UNQ", members = {"denominacion"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Equipo implements Comparable<Equipo> {

    @Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Denominacion")
    @Title(prepend = "Equipo: ")
    @MemberOrder(sequence = "1")
    private String denominacion;

    @Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Modelo")
    @MemberOrder(sequence = "2")
    private String modelo;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Horometro")
    @MemberOrder(sequence = "3")
    private double horometro;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Proximo (horas)")
    @MemberOrder(sequence = "4")
    private double horasProximoMantenimiento;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Ultimo (fecha)")
    @MemberOrder(sequence = "5")
    private LocalDate fechaUltimoMantenimiento;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "%")
    @MemberOrder(sequence = "6")
    private double porcentajeDisponibilidadMensual;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "rpm")
    @MemberOrder(sequence = "7")
    private int rpm;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Presion")
    @MemberOrder(sequence = "8")
    private double presionAceite;

    @Column(allowsNull = "true")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Planta")
    @MemberOrder(sequence = "9")
    private Planta planta;

    @Override
    public String toString() {
        return getDenominacion();
    }

    public int compareTo(final Equipo other) {
        return ComparisonChain.start()
                .compare(this.getDenominacion(), other.getDenominacion())
                .result();
    }

    // ACTUALIZAR EQUIPO
    @Action()
    @ActionLayout(named = "Actualizar")
    public Equipo update(
            final @ParameterLayout(named="Denominacion") String denominacion,
            final @ParameterLayout(named="Modelo") String modelo,
            final @ParameterLayout(named="Horometro") double horometro,
            final @ParameterLayout(named="Horas") double horasProximoMantenimiento,
            final @ParameterLayout(named="Ultimo") LocalDate fechaUltimoMantenimiento,
            final @ParameterLayout(named="Porcentaje") double porcentajeDisponibilidadMensual,
            final @ParameterLayout(named="rpm") int rpm,
            final @ParameterLayout(named="Presion") double presionAceite,

            final @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named="Planta") Planta planta
           ){

        this.denominacion=denominacion;
        this.modelo=modelo;
        this.horometro=horometro;
        this.horasProximoMantenimiento=horasProximoMantenimiento;
        this.fechaUltimoMantenimiento=fechaUltimoMantenimiento;
        this.porcentajeDisponibilidadMensual=porcentajeDisponibilidadMensual;
        this.rpm=rpm;
        this.presionAceite=presionAceite;
        this.planta=planta;

        return this;
    }

    public String default0Update() {return getDenominacion();}
    public String default1Update() {return getModelo();}
    public double default2Update() {return getHorometro();}
    public double default3Update() {return getHorasProximoMantenimiento();}
    public LocalDate default4Update() {return getFechaUltimoMantenimiento();}
    public double default5Update() {return getPorcentajeDisponibilidadMensual();}
    public int default6Update() {return getRpm();}
    public double default7Update() {return getPresionAceite();}

    public Planta default8Update() {return getPlanta();}

    public List<Planta> choices8Update() {return plantaRepositorio.listAll();}

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    PlantaRepositorio plantaRepositorio;

}