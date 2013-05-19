package iDiamondhunter.bows;

import java.util.Map;

import net.minecraft.block.BlockStep;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
        modid = "More Bows",
        name = "More Bows mod",
        version = "1.5_v1"
)
@NetworkMod(
        clientSideRequired = true,
        serverSideRequired = true
)
public class iDiamondhunter
{
    public static Item bowDiamond;
    public static Item bowEnder;
    public static Item bowFlame;
    public static Item bowGold;
    public static Item bowIron;
    public static Item bowMulti;
    public static Item bowStone;
    public static Item bowFrost;
    public static int dID;
    public static int eID;
    public static int fID;
    public static int gID;
    public static int iID;
    public static int mID;
    public static int sID;
    public static int zID;
    protected static boolean z;

    @PostInit
    public void load(FMLPostInitializationEvent var1) {}

    @Init
    public void load(FMLInitializationEvent var1)
    {
        bowDiamond = (ItemBow)(new bowDiamond(dID, 0)).setUnlocalizedName("DiamondBow");
        bowEnder = (ItemBow)(new bowEnder(eID, 4)).setUnlocalizedName("EnderBow");
        bowFlame = (ItemBow)(new bowFlame(fID, 8)).setUnlocalizedName("FlameBow");
        bowGold = (ItemBow)(new bowGold(gID, 12)).setUnlocalizedName("GoldBow");
        bowIron = (ItemBow)(new bowIron(iID, 16)).setUnlocalizedName("IronBow");
        bowMulti = (ItemBow)(new bowMulti(mID, 20)).setUnlocalizedName("MultiBow");
        bowStone = (ItemBow)(new bowStone(sID, 24)).setUnlocalizedName("StoneBow");
        bowFrost = (ItemBow)(new bowFrost(zID, 28)).setUnlocalizedName("FrostBow");
        LanguageRegistry.addName(bowDiamond, "Crystal Bow");
        LanguageRegistry.addName(bowGold, "Golden Bow");
        LanguageRegistry.addName(bowMulti, "Legia Bow");
        LanguageRegistry.addName(bowFlame, "Blazing Bow");
        LanguageRegistry.addName(bowIron, "Iron Bow");
        LanguageRegistry.addName(bowStone, "Reinforced Bow");
        LanguageRegistry.addName(bowEnder, "Ender Bow");
        LanguageRegistry.addName(bowFrost, "Frost Bow");
        GameRegistry.addRecipe(new ItemStack(bowStone, 1), new Object[] {" $*", "#(*", " $*", '#', Item.stick, '*', Item.silk, '$', BlockStep.stone, '(', Item.bow});
        GameRegistry.addRecipe(new ItemStack(bowStone, 1), new Object[] {"*$ ", "*(#", "*$ ", '#', ItemFlintAndSteel.stick, '*', ItemFlintAndSteel.silk, '$', BlockStep.stone, '(', ItemFlintAndSteel.bow});
        GameRegistry.addRecipe(new ItemStack(bowIron, 1), new Object[] {" $*", "$(*", " $*", '*', ItemFlintAndSteel.silk, '$', ItemFlintAndSteel.ingotIron, '(', ItemFlintAndSteel.bow});
        GameRegistry.addRecipe(new ItemStack(bowIron, 1), new Object[] {"*$ ", "*($", "*$ ", '*', ItemFlintAndSteel.silk, '$', ItemFlintAndSteel.ingotIron, '(', ItemFlintAndSteel.bow});
        GameRegistry.addRecipe(new ItemStack(bowGold, 1), new Object[] {" $*", "$(*", " $*", '*', ItemFlintAndSteel.silk, '$', ItemFlintAndSteel.ingotGold, '(', ItemFlintAndSteel.bow});
        GameRegistry.addRecipe(new ItemStack(bowGold, 1), new Object[] {"*$ ", "*($", "*$ ", '*', ItemFlintAndSteel.silk, '$', ItemFlintAndSteel.ingotGold, '(', ItemFlintAndSteel.bow});
        GameRegistry.addRecipe(new ItemStack(bowDiamond, 1), new Object[] {" $*", "I(*", " $*", '*', ItemFlintAndSteel.silk, '$', ItemFlintAndSteel.diamond, 'I', ItemFlintAndSteel.ingotIron, '(', ItemFlintAndSteel.bow});
        GameRegistry.addRecipe(new ItemStack(bowDiamond, 1), new Object[] {"*$ ", "*(I", "*$ ", '*', ItemFlintAndSteel.silk, '$', ItemFlintAndSteel.diamond, 'I', ItemFlintAndSteel.ingotIron, '(', ItemFlintAndSteel.bow});
        GameRegistry.addRecipe(new ItemStack(bowMulti, 1), new Object[] {" $*", "#(*", " $*", '*', ItemFlintAndSteel.silk, '#', ItemFlintAndSteel.ingotIron, '$', bowIron});
        GameRegistry.addRecipe(new ItemStack(bowMulti, 1), new Object[] {"*$ ", "* #", "*$ ", '*', ItemFlintAndSteel.silk, '#', ItemFlintAndSteel.ingotIron, '$', bowIron});
        GameRegistry.addRecipe(new ItemStack(bowFlame, 1), new Object[] {"NB ", "GI ", "NB ", 'G', ItemFlintAndSteel.ingotGold, 'B', ItemFlintAndSteel.blazeRod, 'I', bowIron, 'N', BlockStep.netherrack});
        GameRegistry.addRecipe(new ItemStack(bowFlame, 1), new Object[] {" NB", " GI", " NB", 'G', ItemFlintAndSteel.ingotGold, 'B', ItemFlintAndSteel.blazeRod, 'I', bowIron, 'N', BlockStep.netherrack});
        GameRegistry.addRecipe(new ItemStack(bowEnder, 1), new Object[] {"GP ", "EI ", "GP ", 'G', ItemFlintAndSteel.ingotGold, 'P', ItemFlintAndSteel.enderPearl, 'I', bowIron, 'E', ItemFlintAndSteel.eyeOfEnder});
        GameRegistry.addRecipe(new ItemStack(bowEnder, 1), new Object[] {" GP", " EI", " GP", 'G', ItemFlintAndSteel.ingotGold, 'P', ItemFlintAndSteel.enderPearl, 'I', bowIron, 'E', ItemFlintAndSteel.eyeOfEnder});
        GameRegistry.addRecipe(new ItemStack(bowFrost, 1), new Object[] {" IR", "SER", " IR", 'R', ItemFlintAndSteel.silk, 'I', BlockStep.ice, 'S', ItemFlintAndSteel.snowball, 'E', bowIron});
    }

    public void addRender(Map var1)
    {
        var1.put(EntityFrostArrow.class, new RenderFrostArrow());
    }

    @PreInit
    public void PreLoad(FMLPreInitializationEvent var1)
    {
        Configuration var2 = new Configuration(var1.getSuggestedConfigurationFile());
        var2.load();
        var2.get("general", "z", true).getBoolean(true);
        var2.get("general", "z", true).getInt();
        dID = var2.getItem("Crystal bow", 3196).getInt();
        eID = var2.getItem("Ender bow", 3197).getInt();
        fID = var2.getItem("Blazing bow", 3198).getInt();
        gID = var2.getItem("Golden bow", 3199).getInt();
        iID = var2.getItem("Iron bow", 3200).getInt();
        mID = var2.getItem("Legia bow", 3201).getInt();
        sID = var2.getItem("Reinforced bow", 3202).getInt();
        zID = var2.getItem("Frost bow", 3203).getInt();
        z = var2.get("general", "z", true).getBoolean(true);
        var2.save();
    }
}
