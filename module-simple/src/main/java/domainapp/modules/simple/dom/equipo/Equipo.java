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
import lombok.AccessLevel;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.joda.time.LocalDate;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

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
    @Property(editing = Editing.ENABLED)
    @Title(prepend = "Equipo: ")
    @MemberOrder(sequence = "1")
    private String denominacion;

    @Column(allowsNull = "false", length = 40)
    @Property(editing = Editing.ENABLED)
    @lombok.NonNull
    @MemberOrder(sequence = "2")
    private String modelo;

    @Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @lombok.NonNull
    @MemberOrder(sequence = "3")
    private int horometro;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Horas")
    @MemberOrder(sequence = "4")
    private int horasProximoMantenimiento;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Fecha")
    @MemberOrder(sequence = "5")
    private LocalDate fechaUltimoMantenimiento;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "%")
    @MemberOrder(sequence = "6")
    private int porcentajeDisponibilidadMensual;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "rpm")
    @MemberOrder(sequence = "7")
    private int rpm;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Presion")
    @MemberOrder(sequence = "8")
    private int presionAceite;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
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

}