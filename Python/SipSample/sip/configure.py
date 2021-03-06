import os
import sipconfig

# The name of the SIP build file generated by SIP and used by the build
# system.
build_file = "helloworld.sbf"

# Get the SIP configuration information.
config = sipconfig.Configuration()

# Run SIP to generate the code.
os.system(" ".join([config.sip_bin, "-c", ".", "-b", build_file, "HelloWorld.sip"]))

# Create the Makefile.
makefile = sipconfig.SIPModuleMakefile(config, build_file)

# Add the library we are wrapping.  The name doesn't include any platform
# specific prefixes or extensions (e.g. the "lib" prefix on UNIX, or the
# ".dll" extension on Windows).
makefile.extra_libs = ["hello"]
makefile.extra_lib_dirs = ["../build/cpp/lib"]
makefile.extra_include_dirs = ["../cpp"]

# Generate the Makefile itself.
makefile.generate()
