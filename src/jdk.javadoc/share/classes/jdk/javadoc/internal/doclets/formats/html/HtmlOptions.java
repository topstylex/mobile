/*
 * Copyright (c) 1998, 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.javadoc.internal.doclets.formats.html;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sun.tools.doclint.DocLint;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.Reporter;
import jdk.javadoc.internal.doclets.toolkit.BaseOptions;
import jdk.javadoc.internal.doclets.toolkit.Resources;
import jdk.javadoc.internal.doclets.toolkit.util.DocFile;
import jdk.javadoc.internal.doclets.toolkit.util.Utils;

import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.WARNING;

/**
 * Storage for all options supported by the
 * {@link jdk.javadoc.doclet.StandardDoclet standard doclet},
 * including the format-independent options handled
 * by {@link BaseOptions}.
 *
 */
public class HtmlOptions extends BaseOptions {
    //<editor-fold desc="Option values">
    /**
     * Argument for command-line option {@code --add-stylesheet}.
     */
    public List<String> additionalStylesheets = new ArrayList<>();

    /**
     * Argument for command-line option {@code -bottom}.
     */
    public String bottom = "";

    /**
     * Argument for command-line option {@code -charset}.
     * The META charset tag used for cross-platform viewing.
     */
    public String charset = null;

    /**
     * Argument for command-line option {@code -use}.
     * True if command-line option "-use" is used. Default value is false.
     */
    public boolean classUse = false;

    /**
     * Argument for command-line option {@code -noindex}.
     * False if command-line option "-noindex" is used. Default value is true.
     */
    public boolean createIndex = true;

    /**
     * Argument for command-line option {@code -overview}.
     * This is true if option "-overview" is used or option "-overview" is not
     * used and number of packages is more than one.
     */
    public boolean createOverview = false;

    /**
     * Argument for command-line option {@code -notree}.
     * False if command-line option "-notree" is used. Default value is true.
     */
    public boolean createTree = true;

    /**
     * Arguments for command-line option {@code -Xdoclint} and friends.
     * Collected set of doclint options.
     */
    public Map<Doclet.Option, String> doclintOpts = new LinkedHashMap<>();

    /**
     * Argument for command-line option {@code -Xdocrootparent}.
     */
    public String docrootParent = "";

    /**
     * Argument for command-line option {@code -doctitle}.
     */
    public String docTitle = "";


    /**
     * Argument for command-line option {@code -footer}.
     */
    public String footer = "";

    /**
     * Argument for command-line option {@code -header}.
     */
    public String header = "";

    /**
     * Argument for command-line option {@code -helpfile}.
     */
    public String helpFile = "";

    /**
     * Argument for command-line option {@code -nodeprecated}.
     * True if command-line option "-nodeprecated" is used. Default value is
     * false.
     */
    public boolean noDeprecatedList = false;

    /**
     * Argument for command-line option {@code -nohelp}.
     * True if command-line option "-nohelp" is used. Default value is false.
     */
    public boolean noHelp = false;

    /**
     * Argument for command-line option {@code -nonavbar}.
     * True if command-line option "-nonavbar" is used. Default value is false.
     */
    public boolean noNavbar = false;

    /**
     * Argument for command-line option {@code -nooverview}.
     * True if command-line option "-nooverview" is used. Default value is
     * false
     */
    boolean noOverview = false;

    /**
     * Argument for command-line option {@code -overview}.
     * The overview path specified with "-overview" flag.
     */
    public String overviewPath = null;

    /**
     * Argument for command-line option {@code -packagesheader}.
     */
    public String packagesHeader = "";

    /**
     * Argument for command-line option {@code -splitindex}.
     * True if command-line option "-splitindex" is used. Default value is
     * false.
     */
    public boolean splitIndex = false;

    /**
     * Argument for command-line option {@code -stylesheetfile}.
     */
    public String stylesheetFile = "";

    /**
     * Argument for command-line option {@code -top}.
     */
    public String top = "";

    /**
     * Argument for command-line option {@code -windowtitle}.
     */
    public String windowTitle = "";
    //</editor-fold>

    private HtmlConfiguration config;

    HtmlOptions(HtmlConfiguration config) {
        super(config);
        this.config = config;
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        Resources resources = config.getResources();
        Reporter reporter = config.getReporter();

        List<Option> options = List.of(
                new Option(resources, "--add-stylesheet", 1) {
                    @Override
                    public boolean process(String opt, List<String> args) {
                        additionalStylesheets.add(args.get(0));
                        return true;
                    }
                },

                new Option(resources, "-bottom", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        bottom = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-charset", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        charset = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-doctitle", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        docTitle = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-footer", 1) {
                    @Override
                    public boolean process(String opt, List<String> args) {
                        footer = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-header", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        header = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-helpfile", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        if (noHelp) {
                            reporter.print(ERROR, resources.getText("doclet.Option_conflict",
                                    "-helpfile", "-nohelp"));
                            return false;
                        }
                        if (!helpFile.isEmpty()) {
                            reporter.print(ERROR, resources.getText("doclet.Option_reuse",
                                    "-helpfile"));
                            return false;
                        }
                        helpFile = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-html5") {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        return true;
                    }
                },

                new Option(resources, "-nohelp") {
                    @Override
                    public boolean process(String opt, List<String> args) {
                        noHelp = true;
                        if (!helpFile.isEmpty()) {
                            reporter.print(ERROR, resources.getText("doclet.Option_conflict",
                                    "-nohelp", "-helpfile"));
                            return false;
                        }
                        return true;
                    }
                },

                new Option(resources, "-nodeprecatedlist") {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        noDeprecatedList = true;
                        return true;
                    }
                },

                new Option(resources, "-noindex") {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        createIndex = false;
                        if (splitIndex) {
                            reporter.print(ERROR, resources.getText("doclet.Option_conflict",
                                    "-noindex", "-splitindex"));
                            return false;
                        }
                        return true;
                    }
                },

                new Option(resources, "-nonavbar") {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        noNavbar = true;
                        return true;
                    }
                },

                new Hidden(resources, "-nooverview") {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        noOverview = true;
                        if (overviewPath != null) {
                            reporter.print(ERROR, resources.getText("doclet.Option_conflict",
                                    "-nooverview", "-overview"));
                            return false;
                        }
                        return true;
                    }
                },

                new Option(resources, "-notree") {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        createTree = false;
                        return true;
                    }
                },

                new Option(resources, "-overview", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        overviewPath = args.get(0);
                        if (noOverview) {
                            reporter.print(ERROR, resources.getText("doclet.Option_conflict",
                                    "-overview", "-nooverview"));
                            return false;
                        }
                        return true;
                    }
                },

                new Hidden(resources, "-packagesheader", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        packagesHeader = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-splitindex") {
                    @Override
                    public boolean process(String opt, List<String> args) {
                        splitIndex = true;
                        if (!createIndex) {
                            reporter.print(ERROR, resources.getText("doclet.Option_conflict",
                                    "-splitindex", "-noindex"));
                            return false;
                        }
                        return true;
                    }
                },

                new Option(resources, "--main-stylesheet -stylesheetfile", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        stylesheetFile = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-top", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        top = args.get(0);
                        return true;
                    }
                },

                new Option(resources, "-use") {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        classUse = true;
                        return true;
                    }
                },

                new Option(resources, "-windowtitle", 1) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        windowTitle = args.get(0).replaceAll("<.*?>", "");
                        return true;
                    }
                },

                new XOption(resources, "-Xdoclint") {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        doclintOpts.put(this, DocLint.XMSGS_OPTION);
                        return true;
                    }
                },

                new XOption(resources, "-Xdocrootparent", 1) {
                    @Override
                    public boolean process(String opt, List<String> args) {
                        docrootParent = args.get(0);
                        try {
                            new URL(docrootParent);
                        } catch (MalformedURLException e) {
                            reporter.print(ERROR, resources.getText("doclet.MalformedURL", docrootParent));
                            return false;
                        }
                        return true;
                    }
                },

                new XOption(resources, "doclet.usage.xdoclint-extended", "-Xdoclint:", 0) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        String dopt = opt.replace("-Xdoclint:", DocLint.XMSGS_CUSTOM_PREFIX);
                        doclintOpts.put(this, dopt);
                        if (dopt.contains("/")) {
                            reporter.print(ERROR, resources.getText("doclet.Option_doclint_no_qualifiers"));
                            return false;
                        }
                        if (!DocLint.isValidOption(dopt)) {
                            reporter.print(ERROR, resources.getText("doclet.Option_doclint_invalid_arg"));
                            return false;
                        }
                        return true;
                    }
                },

                new XOption(resources, "doclet.usage.xdoclint-package", "-Xdoclint/package:", 0) {
                    @Override
                    public boolean process(String opt,  List<String> args) {
                        String dopt = opt.replace("-Xdoclint/package:", DocLint.XCHECK_PACKAGE);
                        doclintOpts.put(this, dopt);
                        if (!DocLint.isValidOption(dopt)) {
                            reporter.print(ERROR, resources.getText("doclet.Option_doclint_package_invalid_arg"));
                            return false;
                        }
                        return true;
                    }
                },

                new XOption(resources, "--no-frames") {
                    @Override
                    public boolean process(String opt, List<String> args) {
                        reporter.print(WARNING, resources.getText("doclet.NoFrames_specified"));
                        return true;
                    }
                }
        );
        Set<BaseOptions.Option> allOptions = new TreeSet<>();
        allOptions.addAll(options);
        allOptions.addAll(super.getSupportedOptions());
        return allOptions;
    }

    protected boolean validateOptions() {
        // check shared options
        if (!generalValidOptions()) {
            return false;
        }

        Resources resources = config.getResources();
        Reporter reporter = config.getReporter();

        // check if helpfile exists
        if (!helpFile.isEmpty()) {
            DocFile help = DocFile.createFileForInput(config, helpFile);
            if (!help.exists()) {
                reporter.print(ERROR, resources.getText("doclet.File_not_found", helpFile));
                return false;
            }
        }
        // check if stylesheetFile exists
        if (!stylesheetFile.isEmpty()) {
            DocFile stylesheet = DocFile.createFileForInput(config, stylesheetFile);
            if (!stylesheet.exists()) {
                reporter.print(ERROR, resources.getText("doclet.File_not_found", stylesheetFile));
                return false;
            }
        }
        // check if additional stylesheets exists
        for (String ssheet : additionalStylesheets) {
            DocFile ssfile = DocFile.createFileForInput(config, ssheet);
            if (!ssfile.exists()) {
                reporter.print(ERROR, resources.getText("doclet.File_not_found", ssheet));
                return false;
            }
        }

        // In a more object-oriented world, this would be done by methods on the Option objects.
        // Note that -windowtitle silently removes any and all HTML elements, and so does not need
        // to be handled here.
        Utils utils = config.utils;
        utils.checkJavaScriptInOption("-header", header);
        utils.checkJavaScriptInOption("-footer", footer);
        utils.checkJavaScriptInOption("-top", top);
        utils.checkJavaScriptInOption("-bottom", bottom);
        utils.checkJavaScriptInOption("-doctitle", docTitle);
        utils.checkJavaScriptInOption("-packagesheader", packagesHeader);

        return true;
    }

}
