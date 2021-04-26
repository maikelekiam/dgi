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
package domainapp.modules.simple.dom.compresor;

import com.google.common.collect.ComparisonChain;
import lombok.AccessLevel;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Compresor_tag_UNQ", members = {"tag"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Compresor implements Comparable<Compresor> {

    @Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @PropertyLayout(named = "TAG")
    @Title(prepend = "TAG: ")
    @MemberOrder(sequence = "1")
    private String tag;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Temp Succion 1")
    @MemberOrder(sequence = "2")
    private double tempSuccion1;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "ps1")
    @MemberOrder(sequence = "3")
    private double ps1;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Temp Succion 2")
    @MemberOrder(sequence = "4")
    private double tempSuccion2;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "ps2")
    @MemberOrder(sequence = "5")
    private double ps2;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Temp Succion 3")
    @MemberOrder(sequence = "6")
    private double tempSuccion3;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "ps3")
    @MemberOrder(sequence = "7")
    private double ps3;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "pd")
    @MemberOrder(sequence = "8")
    private double pd;

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    @PropertyLayout(named = "Caudal diario")
    @MemberOrder(sequence = "9")
    private double caudalDiario;

    @Override
    public String toString() {
        return getTag();
    }

    public int compareTo(final Compresor other) {
        return ComparisonChain.start()
                .compare(this.getTag(), other.getTag())
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